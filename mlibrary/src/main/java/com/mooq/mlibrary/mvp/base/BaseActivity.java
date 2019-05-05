package com.mooq.mlibrary.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mooq.mlibrary.mvp.presenter.BaseActivityPresenter;
import com.mooq.mlibrary.mvp.view.BaseActivityView;
import com.mooq.mlibrary.custom.ProgressDialog;

/**
 * Created by moq.
 * on 2019/4/29
 */
public abstract class BaseActivity<P extends BaseActivityPresenter> extends AppCompatActivity implements BaseActivityView{

	protected P mPresenter;

	protected abstract P createPresenter();

	private Context mContext = null;

	protected ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPresenter = createPresenter();
		mContext = this;
		mPresenter.onAttach(this);
		onCreateActivity(savedInstanceState);
		mPresenter.onCreate(this);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mPresenter != null)
			mPresenter.onDetach();
		if (null != mProgressDialog) {
			mProgressDialog.dismiss();
			mProgressDialog.cancel();
			mProgressDialog = null;
		}

		mPresenter.onDestroy(this);
	}

	@Override
	public void showProcessLoading(String msg, boolean cancelEnable) {
		if (isFinishing()) {
			return;
		}
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setCancelable(cancelEnable);
		mProgressDialog.setCanceledOnTouchOutside(cancelEnable);
		mProgressDialog.setMessage(msg);
		mProgressDialog.show();
	}

	@Override
	public void dismissProcessLoading() {
		if (mProgressDialog !=null){
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}

	protected abstract void onCreateActivity(Bundle savedInstanceState);
}
