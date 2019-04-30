package com.mooq.rxbusretrofitmvptest;

import com.mooq.mlibrary.network.retrofit.RetrofitUtil;

/**
 * Created by moq.
 * on 2019/4/30
 */
public class ApiServiceImpl {
	private ApiServiceImpl() {

	}
	public static APIService getInstance() {
		return createAPIService.apiService;
	}

	/**
	 * Retrofit生成接口对象.
	 */
	private static class createAPIService {
		//Retrofit会根据传入的接口类.生成实例对象.
		private static final APIService apiService = RetrofitUtil.getRetrofit().create(APIService.class);
	}
}
