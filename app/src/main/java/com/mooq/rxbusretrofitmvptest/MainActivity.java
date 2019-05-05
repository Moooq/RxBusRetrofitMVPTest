package com.mooq.rxbusretrofitmvptest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mooq.mlibrary.mvp.base.BaseActivity;

public class MainActivity extends BaseActivity<MainActivityPresenter> implements MainActivityView,View.OnClickListener{


	Button btnTest;
	TextView tvMsg;

	@Override
	protected MainActivityPresenter createPresenter() {
		return new MainActivityPresenter(this);
	}

	@Override
	protected void onCreateActivity(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		btnTest = (Button) findViewById(R.id.btn_test);
		tvMsg = (TextView) findViewById(R.id.tv_msg);
		btnTest.setOnClickListener(this);
	}

	@Override
	public void setDayNightMode(boolean isNight) {

	}


	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_test:
				mPresenter.requestVideos();
				break;
		}
	}

	@Override
	public void showResult(String msg) {
		tvMsg.setText(msg);
	}
}
