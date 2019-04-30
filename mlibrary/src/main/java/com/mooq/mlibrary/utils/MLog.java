package com.mooq.mlibrary.utils;

import android.content.Context;

/**
 * Created by moq.
 * on 2019/4/29
 */
public class MLog {

	private static Context mContext;

	private static boolean isDebug;

	public static void init(Context context, boolean isDebug){
		mContext = context;
		isDebug = isDebug;
	}

	public static void i(String tag, String msg){
		if (msg!=null){
			android.util.Log.i(tag, msg);
		}
	}

	public static void d(String tag, String msg){
		if (msg!=null){
			android.util.Log.d(tag, msg);
		}
	}

	public static void e(String tag, String msg){
		if (msg!=null){
			android.util.Log.e(tag, "ERROR===="+msg);
		}
	}

}
