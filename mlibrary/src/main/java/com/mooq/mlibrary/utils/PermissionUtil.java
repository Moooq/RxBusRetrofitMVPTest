package com.mooq.mlibrary.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by moq.
 * on 2019/5/5
 */
public class PermissionUtil {

	private static final int CAMERA_PERMISSION_CODE = 0;
	private static final String CAMERA_PERMISSION = Manifest.permission.CAMERA;

	/**
	 * 相机权限是否开启
	 * @param activity
	 * @return
	 */
	public static boolean hasCameraPermission(Activity activity){
		return ContextCompat.checkSelfPermission(activity,CAMERA_PERMISSION)
				== PackageManager.PERMISSION_GRANTED;
	}

	/**
	 * 请求相机权限
	 * @param activity
	 */
	public static void requestCameraPermission(Activity activity){
		ActivityCompat.requestPermissions(
				activity, new String[]{CAMERA_PERMISSION},CAMERA_PERMISSION_CODE);
	}

	/**
	 * 展示申请权限的相应解释
	 * @param activity
	 * @return
	 */
	public static boolean shouldShowRequestPermissionRationale(Activity activity) {
		return ActivityCompat.shouldShowRequestPermissionRationale(activity, CAMERA_PERMISSION);
	}

	/**
	 * 打开设置界面
	 *
	 * @param activity
	 */
	public static void launchPermissionSettings(Activity activity) {
		Intent intent = new Intent();
		intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
		activity.startActivity(intent);
	}
}
