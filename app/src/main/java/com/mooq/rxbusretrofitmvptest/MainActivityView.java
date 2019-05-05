package com.mooq.rxbusretrofitmvptest;

import com.mooq.mlibrary.mvp.view.BaseActivityView;

/**
 * Created by moq.
 * on 2019/5/5
 */
public interface MainActivityView extends BaseActivityView {
	void showResult(String msg);
}
