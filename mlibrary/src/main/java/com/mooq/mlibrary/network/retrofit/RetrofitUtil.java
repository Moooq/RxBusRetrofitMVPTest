package com.mooq.mlibrary.network.retrofit;

import android.content.Context;
import android.text.TextUtils;

import com.mooq.mlibrary.utils.FileUtil;
import com.mooq.mlibrary.utils.MLog;
import com.mooq.mlibrary.utils.NetworkUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

	public static Context mContext;

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

			builder.cookieJar(new CookieJar() {
				private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>(16);

				@Override
				public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
					cookieStore.put(HttpUrl.parse(url.host()),cookies);
				}

				@Override
				public List<Cookie> loadForRequest(HttpUrl url) {
					List<Cookie> cookies = cookieStore.get(url.host());
					return cookies !=null?cookies:new ArrayList<Cookie>();
				}
			});


			//添加Cache拦截器，有网时添加到缓存中，无网时取出缓存
			File file = FileUtil.getInstance().getCacheFolder();
			Cache cache=new Cache(file,1024*1024*100);
			builder.cache(cache).addInterceptor(new Interceptor() {
				@Override
				public Response intercept(Chain chain) throws IOException {
					Request request = chain.request();
					if (!NetworkUtil.isNetwork(mContext)){
						Request newRequest = request.newBuilder()
								.cacheControl(CacheControl.FORCE_CACHE)
								.build();

						return chain.proceed(newRequest);
					}
					else{
						int maxTime =24*60*60;
						Response response=chain.proceed(request);
						Response newResponse = response.newBuilder()
								.header("Cache-Control","public, only-if-cached, max-stale="+maxTime)
								.removeHeader("Progma")
								.build();

						return newResponse;
					}
				}
			});

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

	public static RetrofitUtil getInstance(Context context) {
		return getInstance("",context);
	}

	public static RetrofitUtil getInstance(String baseUrl,Context context) {
		if (retrofitUtil == null) {
			mContext = context;
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
