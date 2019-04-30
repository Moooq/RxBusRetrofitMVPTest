package com.mooq.mlibrary.network.retrofit;

import android.app.Application;
import android.text.TextUtils;

import com.mooq.mlibrary.utils.MLog;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by moq.
 * on 2019/4/30
 */
public class RetrofitUtil {
	/**
	 * 服务器地址
	 */
	private static String API_HOST;
	private static Application mContext;
	private RetrofitUtil() {

	}

	public static synchronized void init(String baseUrl, Application context){
		if (TextUtils.isEmpty(baseUrl)) return;

		mContext = context;
		API_HOST = baseUrl;
		Instanace.retrofit = Instanace.getInstanace();

	}

	public static Retrofit getRetrofit() {
		return Instanace.retrofit;
	}
	//静态内部类,保证单例并在调用getRetrofit方法的时候才去创建.
	private static class Instanace {
		private static Retrofit retrofit = getInstanace();
		private static Retrofit getInstanace() {
			HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger(){
				@Override
				public void log(String message) {
					MLog.d("RxJava", message);
				}
			});
			OkHttpClient client = new OkHttpClient.Builder()
					.addInterceptor(interceptor)
					.build();
			retrofit = new Retrofit.Builder()
					.client(client)
					.baseUrl(API_HOST)
					.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
					.build();
			return retrofit;
		}
	}

}
