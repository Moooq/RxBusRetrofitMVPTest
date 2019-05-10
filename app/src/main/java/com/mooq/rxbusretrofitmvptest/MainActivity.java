package com.mooq.rxbusretrofitmvptest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mooq.customlibrary.ScrollNoticeView;
import com.mooq.mlibrary.mvp.base.BaseActivity;
import com.mooq.mlibrary.utils.MLog;

import java.util.List;

public class MainActivity extends BaseActivity<MainActivityPresenter> implements MainActivityView,View.OnClickListener{

	private static final String TAG = "MainActivity";

	Button btnTest;
	TextView tvMsg;
	ScrollNoticeView snvNotice;

	@Override
	protected MainActivityPresenter createPresenter() {
		return new MainActivityPresenter(this);
	}

	@Override
	protected void onCreateActivity(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main);
		btnTest = (Button) findViewById(R.id.btn_test);
		tvMsg = (TextView) findViewById(R.id.tv_msg);
		snvNotice = (ScrollNoticeView) findViewById(R.id.snv_notice);
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
			case R.id.snv_notice:
				MLog.d(TAG,"notice_text:"+snvNotice.getText());
				break;
		}
	}

	@Override
	public void showResult(String msg) {
		tvMsg.setText(msg);
	}

	@Override
	public void showResult(List<String> data) {
		snvNotice.setNoticeList(data);
	}
}
