package com.mooq.mlibrary.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.lang.reflect.Method;

/**
 * Created by moq.
 * on 2019/4/30
 */
public class Utils {

	//获得设备ID
	public static String getDeviceId(Context context) {
		TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return "";
		}
		String eid = TelephonyMgr.getDeviceId();
		return eid;
	}

	//判断是否有NavigationBar
	public static boolean checkDeviceHasNavigationBar(@NonNull Context context) {
		boolean hasNavigationBar = false;
		Resources rs = context.getResources();
		int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
		if (id > 0) {
			hasNavigationBar = rs.getBoolean(id);
		}
		try {
			Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
			Method m = systemPropertiesClass.getMethod("get", String.class);
			String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
			if ("1".equals(navBarOverride)) {
				hasNavigationBar = false;
			} else if ("0".equals(navBarOverride)) {
				hasNavigationBar = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hasNavigationBar;
	}

	//  获取NavigationBar的高度：
	public static int getNavigationBarHeight(@NonNull Context context) {
		int navigationBarHeight = 0;
		Resources rs = context.getResources();
		int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
		if (id > 0 && checkDeviceHasNavigationBar(context)) {
			navigationBarHeight = rs.getDimensionPixelSize(id);
		}
		return navigationBarHeight;
	}

	/**
	 * 关闭软键盘
	 *
	 * @param activity
	 */
	public static void hintKbTwo(Activity activity) {
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive() && activity.getCurrentFocus() != null) {
			if (activity.getCurrentFocus().getWindowToken() != null) {
				imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
	}

	public static void hideSoftInput(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
	}

	public static void showSoftInput(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		//imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	public static void hintKbTwo(Dialog dialog) {
		InputMethodManager imm = (InputMethodManager) dialog.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			final View currentFocus = dialog.getCurrentFocus();
			final IBinder windowToken = currentFocus != null ?
					currentFocus.getWindowToken() : dialog.getCurrentFocus().getWindowToken();
			if (windowToken != null) {
				imm.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
	}

	/**
	 * 用字符串生成二维码
	 *
	 * @param str
	 * @param imageView 显示二维码的imageview
	 * @return
	 * @throws WriterException
	 * @author zhouzhe@lenovo-cw.com
	 */
	public static Bitmap Create2DCode(String str, ImageView imageView) throws WriterException {
		//生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
		ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
		BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, layoutParams.width, layoutParams.height);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		//二维矩阵转为一维像素数组,也就是一直横着排了
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					pixels[y * width + x] = 0xff000000;
				}
			}
		}
		MLog.i("Create2DCode", height + "---" + width);
		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		//通过像素数组生成bitmap,具体参考api
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}
//	/**
//	 * 在二维码中间添加Logo图案
//	 */
//	public static Bitmap addLogo(Bitmap src, Bitmap logo) {
//		if (src == null) {
//			return null;
//		}
//
//		if (logo == null) {
//			return src;
//		}
//
//		//获取图片的宽高
//		int srcWidth = src.getWidth();
//		int srcHeight = src.getHeight();
//		int logoWidth = logo.getWidth();
//		int logoHeight = logo.getHeight();
//
//		if (srcWidth == 0 || srcHeight == 0) {
//			return null;
//		}
//
//		if (logoWidth == 0 || logoHeight == 0) {
//			return src;
//		}
//
//		//logo大小为二维码整体大小的1/5
//		float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
//		Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
//		try {
//			Canvas canvas = new Canvas(bitmap);
//			canvas.drawBitmap(src, 0, 0, null);
//			canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
//			canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);
//			canvas.save(Canvas.ALL_SAVE_FLAG);
//			canvas.restore();
//		} catch (Exception e) {
//			bitmap.recycle();
//			bitmap = null;
//			e.getStackTrace();
//		}
//
//		return bitmap;
//	}

}
