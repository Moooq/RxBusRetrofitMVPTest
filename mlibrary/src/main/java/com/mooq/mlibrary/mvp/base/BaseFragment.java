package com.mooq.mlibrary.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mooq.mlibrary.mvp.presenter.BaseFragmentPresenter;
import com.mooq.mlibrary.mvp.view.BaseFragmentView;

/**
 * Created by moq.
 * on 2019/4/29
 */
public abstract class BaseFragment<P extends BaseFragmentPresenter> extends Fragment implements BaseFragmentView {
	protected P mPresenter;
	public abstract P initPresenter();

	protected Context mContext;
	protected Bundle mBundle;

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mBundle != null) {
			outState.putBundle("bundle", mBundle);
		}
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mContext = context;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//获取bundle,并保存起来
		if (savedInstanceState != null) {
			mBundle = savedInstanceState.getBundle("bundle");
		} else {
			mBundle = getArguments() == null ? new Bundle() : getArguments();
		}
		//创建presenter
		mPresenter = initPresenter();
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mPresenter.onAttach(this);
		mPresenter.onCreate();
	}

	@Override
	public void onDestroyView() {
		mPresenter.onDestroy();
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		mPresenter.onDetach();
		super.onDetach();
	}

	@Override
	public void startFragment(Fragment tofragment) {
		startFragment(tofragment, null);
	}

	@Override
	public void startFragment(Fragment tofragment, String tag) {
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		fragmentTransaction.hide(this).add(android.R.id.content, tofragment, tag);
		fragmentTransaction.addToBackStack(tag);
		fragmentTransaction.commitAllowingStateLoss();
	}

	public void onBack() {
		getFragmentManager().popBackStack();
	}

	public abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

	@Override
	public Context getContext() {
		return mContext;
	}

	@Override
	public Bundle getBundle() {
		return mBundle;
	}

	@Override
	public BaseFragment getFragment() {
		return this;
	}


}
