package com.mooq.mlibrary.mvp.view;

/**
 * Created by moq.
 * on 2019/4/29
 */
public interface BaseView {

	void showProcessLoading(String msg, boolean cancelEnable);
	void dismissProcessLoading();

	/**
	 * 切换日夜模式
	 * @param isNight
	 */
	void setDayNightMode(boolean isNight);
}