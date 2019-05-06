package com.mooq.rxbusretrofitmvptest.network;

import android.content.Context;

import com.mooq.mlibrary.network.retrofit.RetrofitUtil;
import com.mooq.rxbusretrofitmvptest.Configs;
import com.mooq.rxbusretrofitmvptest.network.APIService;

/**
 * Created by moq.
 * on 2019/4/30
 */
public class ApiServiceImpl {
	private ApiServiceImpl() {

	}
	public static APIService getInstance(Context context) {
		return RetrofitUtil.getInstance(Configs.ApiConfig.BASE_URL,context).create(APIService.class);
	}
}
