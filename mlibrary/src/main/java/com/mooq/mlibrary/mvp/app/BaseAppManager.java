package com.mooq.mlibrary.mvp.app;

import android.app.Activity;

import com.mooq.mlibrary.utils.MLog;

import java.util.Stack;

/**
 * Created by moq.
 * on 2019/4/30
 */
public class BaseAppManager {

	private String TAG = "BaseAppManager";
	private static Stack<Activity> activityStack;
	private static BaseAppManager instance;

	public static BaseAppManager getAppManager(){
		if (instance == null)
			instance = new BaseAppManager();
		return instance;
	}

	/**
	 * 添加Activity到栈
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
		MLog.d(TAG, "ActivityList.onCreate(" + activity.getLocalClassName() + "),size() = " + activityStack.size() + ";") ;

	}

	/**
	 * 获取当前Activity（栈顶Activity）
	 */
	public Activity currentActivity() {
		if (activityStack == null || activityStack.isEmpty()) {
			return null;
		}
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 结束指定的Activity(重载)
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			MLog.d(TAG, "ActivityList.onDestroy(" + activity.getLocalClassName() + "),size() = " + activityStack.size() + ";") ;
			activity = null;
		}
	}


}
