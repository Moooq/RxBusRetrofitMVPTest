package com.mooq.rxbusretrofitmvptest;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.mooq.mlibrary.mvp.presenter.BaseActivityPresenter;
import com.mooq.mlibrary.mvp.view.BaseActivityView;
import com.mooq.mlibrary.network.BaseObserver;
import com.mooq.mlibrary.network.model.BaseResponse;
import com.mooq.mlibrary.utils.MLog;
import com.mooq.rxbusretrofitmvptest.network.ApiServiceImpl;
import com.mooq.rxbusretrofitmvptest.network.response.VideosResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by moq.
 * on 2019/4/29
 */
public class MainActivityPresenter<V extends MainActivityView> extends BaseActivityPresenter<MainActivityView>{

	private MainActivityView iView;

	public MainActivityPresenter(MainActivityView view) {
		onAttach(view);
	}

	public void onAttach(MainActivityView view) {
		iView = view;
	}

	public void requestVideos(){

		Observable observable = ApiServiceImpl.getInstance(mContext).getAllVedioBy(true);

		addSubscriptionStartRequest(observable, new BaseObserver<List<LinkedTreeMap>>() {
			@Override
			public void onSuccess(List<LinkedTreeMap> data) {
				MLog.d("MainActivityPresenter", "HTTP result：ize ：" + data.size());
				iView.showResult(data.toString());
				List<String> list = new ArrayList<>();
				for (int i = 0;i<data.size();i++){
					LinkedTreeMap linkedTreeMap = data.get(i);
					list.add((String)linkedTreeMap.get("name"));
				}
				iView.showResult(list);
			}

			@Override
			public void onFailure(String msg) {
				MLog.d(getClass().getSimpleName(), "HTTP result：failed msg:"+msg);
			}
		});
	}
}
