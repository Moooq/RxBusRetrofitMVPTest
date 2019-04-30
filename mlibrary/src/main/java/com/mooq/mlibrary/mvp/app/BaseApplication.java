package com.mooq.mlibrary.mvp.app;

import android.app.Application;

import com.mooq.mlibrary.utils.MLog;

/**
 * Created by moq.
 * on 2019/4/29
 */
public class BaseApplication extends Application {
	private static BaseApplication instance;



	@Override
	public void onCreate() {
		super.onCreate();

		instance = this;

		MLog.init(getApplicationContext(),true);
	}


}
