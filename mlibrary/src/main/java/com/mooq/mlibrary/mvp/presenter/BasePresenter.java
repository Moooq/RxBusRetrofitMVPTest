package com.mooq.mlibrary.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.mooq.mlibrary.mvp.view.BaseView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by moq.
 * on 2019/4/29
 */
public abstract class BasePresenter<V extends BaseView> {
	public V mView;

	private Context mContext;

	private CompositeDisposable mCompositeDisposable;

	public void addSubscriptionStartRequest(Observable observable, DisposableObserver observer){
		if (mCompositeDisposable == null)
			mCompositeDisposable = new CompositeDisposable();

		mCompositeDisposable.add(observer);
		observable.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeWith(observer);
	}

	public void onAttach(V view){
		this.mView = view;
	}

	public void onDetach(){
		this.mView = null;
		onUnSubscribe();
	}

	public void onUnSubscribe(){
		if (mCompositeDisposable != null)
			mCompositeDisposable.dispose();
	}

	public abstract void onCreate(Activity activity);

	public abstract void onDestroy(Activity activity);

	public abstract void onSaveInstanceState(Bundle outState);
}
