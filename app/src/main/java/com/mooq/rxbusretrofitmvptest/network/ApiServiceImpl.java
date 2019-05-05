package com.mooq.rxbusretrofitmvptest.network;

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
	public static APIService getInstance() {
		return RetrofitUtil.getInstance(Configs.ApiConfig.BASE_URL).create(APIService.class);
	}
}
