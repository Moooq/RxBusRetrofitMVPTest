package com.mooq.mlibrary.mvp.presenter;

import android.app.Activity;
import android.os.Bundle;

import com.mooq.mlibrary.mvp.app.BaseAppManager;
import com.mooq.mlibrary.mvp.view.BaseActivityView;

/**
 * Created by moq.
 * on 2019/4/29
 */
public class BaseActivityPresenter<V extends BaseActivityView> extends BasePresenter {
	@Override
	public void onCreate(Activity activity) {
		BaseAppManager.getAppManager().addActivity(activity);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

	}

	@Override
	public void onDestroy(Activity activity) {
		BaseAppManager.getAppManager().finishActivity(activity);
	}
}
