package com.mooq.mlibrary.mvp.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mooq.mlibrary.R;

/**
 * Created by moq.
 * on 2019/4/29
 */
public class ProgressDialog extends Dialog {

	private Context mContext;
	private TextView titleText;

	public ProgressDialog(@NonNull Context context) {
		super(context, R.style.progress_dialog);
		this.mContext = context;
	}

	private void setDialogContentView() {
		View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_progress, null);   // 加载自己定义的布局
		titleText = view.findViewById(R.id.tv_loading_msg);
		this.setContentView(view);
		this.getWindow().getAttributes().gravity = Gravity.CENTER;
		this.setCancelable(true);
		this.setCanceledOnTouchOutside(true);
		//获取当前Activity所在的窗体
		Window dialogWindow = getWindow();
		//获得窗体的属性
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.width = -1;
		//将属性设置给窗体
		dialogWindow.setAttributes(lp);

	}

	public void setMessage(String msg) {
		if (titleText == null) {
			return;
		}
		if (!TextUtils.isEmpty(msg)) {
			titleText.setText(msg);
			titleText.setVisibility(View.VISIBLE);
		} else {
			titleText.setVisibility(View.GONE);
		}
	}

}
