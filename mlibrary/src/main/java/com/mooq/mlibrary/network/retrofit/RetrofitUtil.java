package com.mooq.mlibrary.network.retrofit;

import android.text.TextUtils;

import com.mooq.mlibrary.utils.MLog;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by moq.
 * on 2019/4/30
 */
public class RetrofitUtil{
	/**
	 * 服务器地址
	 */
	private static String API_HOST;

	private static final int DEFAULT_TIME_OUT = 5;//超时时间 5s
	private static final int DEFAULT_READ_TIME_OUT = 10;

	private static RetrofitUtil retrofitUtil;

	private static Retrofit retrofit;

	public RetrofitUtil(){
		new RetrofitUtil("");
	}

	public RetrofitUtil(String baseUrl) {
		if (retrofit == null) {
			if (!TextUtils.isEmpty(baseUrl))
				API_HOST = baseUrl;

			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间
			builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
			builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间

			HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger(){
				@Override
				public void log(String message) {
					MLog.d("RetrofitUtil", message);
				}
			});
			interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
			// 添加公共参数拦截器
//			HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder()
//					.addHeaderParams("paltform", "android")
//					.addHeaderParams("userToken", "1234343434dfdfd3434")
//					.addHeaderParams("userId", "123445")
//					.build();
			builder.addInterceptor(interceptor);

			// if (BuildConfig.DEBUG) {
			// Log信息拦截器
			HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
			loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
			//设置 Debug Log 模式
			builder.addInterceptor(loggingInterceptor);
			//}
			MLog.d("RetrofitUtil", "------" + builder);

			// 创建Retrofit
			retrofit = new Retrofit.Builder()
					.client(builder.build())
					.baseUrl(API_HOST)
					.addConverterFactory(GsonConverterFactory.create())
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build();
		}
	}

	public static RetrofitUtil getInstance() {
		return getInstance("");
	}

	public static RetrofitUtil getInstance(String baseUrl) {
		if (retrofitUtil == null) {
			synchronized (RetrofitUtil.class) {
				if (retrofitUtil == null) {
					retrofitUtil = new RetrofitUtil(baseUrl);
				}
			}
		}
		return retrofitUtil;
	}

	public <T> T create(Class<T> service) {
		return retrofit.create(service);
	}

}
