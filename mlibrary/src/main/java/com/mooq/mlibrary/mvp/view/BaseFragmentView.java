package com.mooq.mlibrary.mvp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mooq.mlibrary.mvp.base.BaseFragment;

/**
 * Created by moq.
 * on 2019/4/29
 */
public interface BaseFragmentView extends BaseView{
	void startFragment(Fragment tofragment);

	void startFragment(Fragment tofragment, String tag);

	Bundle getBundle();

	BaseFragment getFragment();
}
