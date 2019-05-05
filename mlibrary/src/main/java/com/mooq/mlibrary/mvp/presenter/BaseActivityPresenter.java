package com.mooq.mlibrary.mvp.presenter;

import android.app.Activity;
import android.os.Bundle;

import com.mooq.mlibrary.mvp.app.BaseAppManager;
import com.mooq.mlibrary.mvp.view.BaseActivityView;
import com.mooq.mlibrary.mvp.view.BaseView;

/**
 * Created by moq.
 * on 2019/4/29
 */
public class BaseActivityPresenter<V extends BaseActivityView> extends BasePresenter<BaseActivityView> {
	@Override
	public void onAttach(BaseActivityView view) {
		mView = view;
	}

	@Override
	public void onCreate(Activity activity) {
		BaseAppManager.getAppManager().addActivity(activity);
		mContext = activity.getBaseContext();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

	}

	@Override
	public void onDestroy(Activity activity) {
		BaseAppManager.getAppManager().finishActivity(activity);
	}
}
