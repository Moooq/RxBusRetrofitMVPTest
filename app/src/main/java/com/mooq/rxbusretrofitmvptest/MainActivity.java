package com.mooq.rxbusretrofitmvptest;

import android.os.Bundle;

public class MainActivity extends BaseActivity<MainActivityPresenterImpl> {
	@Override
	protected MainActivityPresenterImpl initPresenter() {
		return new MainActivityPresenterImpl();
	}

	@Override
	protected void onCreateActivity(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
	}
}
