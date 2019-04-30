package com.mooq.mlibrary.network;


import com.mooq.mlibrary.utils.MLog;
import com.mooq.mlibrary.network.model.BaseResponse;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Created by moq.
 * on 2019/4/29
 */
public abstract class BaseObserver<M> extends DisposableObserver<BaseResponse<M>>{

	public abstract void onSuccess(M model);

	public abstract void onFailure(String msg);

	@Override
	public void onError(Throwable e) {
		e.printStackTrace();
		if (e instanceof HttpException){
			HttpException httpException = (HttpException) e;
			int code = httpException.code();
			String msg = httpException.getMessage();
			MLog.d(getClass().getName(), "onError: code="+code+" msg="+msg);
			switch(code){
				case 504:
					msg = "网络不给力";
					break;
				case 502:
				case 500:
				case 404:
					msg = "服务器异常，请稍后再试";
					break;
					default:
						break;
			}
			onFailure(msg);
		}else{
			onFailure(e.getMessage());
		}
	}

	@Override
	public void onNext(BaseResponse<M> response) {
		onSuccess(response.getData());
	}

	@Override
	public void onComplete() {

	}
}
