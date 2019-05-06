package com.mooq.rxbusretrofitmvptest;

import com.mooq.mlibrary.mvp.view.BaseActivityView;

import java.util.List;

/**
 * Created by moq.
 * on 2019/5/5
 */
public interface MainActivityView extends BaseActivityView {
	void showResult(String msg);
	void showResult(List<String> data);
}
