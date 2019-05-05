package com.mooq.mlibrary.utils;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by moq.
 * on 2019/4/30
 */
public class FileUtil {

	private static String mSDCardFolderPath;
	private static String mImgFolderPath;
	private static String mApkFolderPath;
	private static String mCacheFolderPath;
	private static String mLogFolderPath;

	public Context context;

	public static FileUtil getInstance() {

		return FileUtilHolder.instance;
	}

	public void init(Context context) {

		this.context = context;

		mImgFolderPath = getImgFolderPath();
		mApkFolderPath = getApkFolderPath();
		mCacheFolderPath = getCacheFolderPath();
		mLogFolderPath = getLogFolderPath();

		mkdirs(mImgFolderPath);
		mkdirs(mApkFolderPath);
		mkdirs(mCacheFolderPath);
		mkdirs(mLogFolderPath);
	}

	private String getSDCardFolderPath() {
		if (TextUtils.isEmpty(mSDCardFolderPath)) {
			mSDCardFolderPath = Environment.getExternalStorageDirectory().getPath() + "/"+AppUtil.getAppName(context);
		}

		return mSDCardFolderPath;
	}

	public File mkdirs(@NonNull String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}

		return file;
	}

	public String getCacheFolderPath() {
		if (TextUtils.isEmpty(mCacheFolderPath)) {
			mCacheFolderPath = getSDCardFolderPath() + "/Cache/";
		}
		return mCacheFolderPath;
	}

	public String getImgFolderPath() {
		if (TextUtils.isEmpty(mImgFolderPath)) {
			mImgFolderPath = getSDCardFolderPath() + "/Img/";
		}
		return mImgFolderPath;
	}

	public String getApkFolderPath() {
		if (TextUtils.isEmpty(mApkFolderPath)) {
			mApkFolderPath = getSDCardFolderPath() + "/Apk/";
		}
		return mApkFolderPath;
	}

	public String getLogFolderPath() {
		if (TextUtils.isEmpty(mLogFolderPath)) {
			mLogFolderPath = getSDCardFolderPath() + "/Log/";
		}
		return mLogFolderPath;
	}

	public File getCacheFolder(){
		return mkdirs(getCacheFolderPath());
	}

	private static class FileUtilHolder {
		private static FileUtil instance = new FileUtil();
	}

	/**
	 * 判断SD卡是否挂载
	 */
	public static boolean isSDCardAvailable() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			return true;
		} else {
			return false;
		}
	}

	public static byte[] readFile(File file) {
		// 需要读取的文件，参数是文件的路径名加文件名
		if (file.isFile()) {
			// 以字节流方法读取文件
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				// 设置一个，每次 装载信息的容器
				byte[] buffer = new byte[1024];
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				// 开始读取数据
				int len = 0;// 每次读取到的数据的长度
				while ((len = fis.read(buffer)) != -1) {// len值为-1时，表示没有数据了
					// append方法往sb对象里面添加数据
					outputStream.write(buffer, 0, len);
				}
				// 输出字符串
				return outputStream.toByteArray();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static File getFile(Context context, String path) {
		if (FileUtil.isSDCardAvailable()) {
			return new File(context.getExternalCacheDir(), path);
		} else {
			return new File(context.getCacheDir(), path);
		}
	}

	public static boolean isWriteable(String path) {
		try {
			if (TextUtils.isEmpty(path)) {
				return false;
			}
			File f = new File(path);
			return f.exists() && f.canWrite();
		} catch (Exception e) {
			MLog.e("FileUtil", e.toString());
			return false;
		}
	}

	/**
	 * 将数据存储为文件
	 *
	 * @param data 数据
	 */
	public static File byteToFile(byte[] data, File f) {
		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(data);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			MLog.e("FileUtil", "create file error" + e);
		}
		return f;
	}

	public static File createFile(Context context, String mFileDir, String mFileName) {
		File dir = getFile(context, mFileDir);
		if (dir != null) {
			File file = new File(dir, mFileName);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return file;
		}
		return null;
	}

}
