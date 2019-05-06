package com.mooq.mlibrary.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.mooq.mlibrary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moq.
 * on 2019/5/6
 */
public class ScrollNoticeView extends LinearLayout{
	private static final String TAG = "ScrollNoticeView";
	private Context mContext;
	private ViewFlipper mViewFlipper;
	private View mScrollTitleView;
	private final int DEFAULT_ANIMATION_DURATION = 1000; // 动画时长
	private final int DEFAULT_NOTICE_SPACE = 5000; // 公告切换时长
	private final int DEFAULT_TEXT_COLOR = 0xff000000;  // 默认字体颜色

	private int mNoticeAnim = DEFAULT_ANIMATION_DURATION;
	private int mNoticeDuration = DEFAULT_NOTICE_SPACE;
	private int mTextColor = DEFAULT_TEXT_COLOR;
	private float mTextSize;

	private AnimationSet mInAnimSet;
	private AnimationSet mOutAnimSet;

	private List<String> mNoticeList;


	public ScrollNoticeView(Context context) {
		super(context);
		mContext = context;
		init(null);
	}
	public ScrollNoticeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init(attrs);
	}

	private void init(AttributeSet attrs) {
		TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.ScrollNoticeView);
		mTextColor = array.getColor(R.styleable.ScrollNoticeView_textcolor, DEFAULT_TEXT_COLOR);
		mTextSize = array.getDimension(R.styleable.ScrollNoticeView_textsize, 5);
		mNoticeDuration = array.getInt(R.styleable.ScrollNoticeView_durationtime,DEFAULT_NOTICE_SPACE);
		mNoticeAnim = array.getInt(R.styleable.ScrollNoticeView_animtime,DEFAULT_ANIMATION_DURATION);
		array.recycle();

		initAnim();
		bindLinearLayout();
//		bindNotices();
	}

	private void initAnim(){
		mInAnimSet = new AnimationSet(false);
		TranslateAnimation translateAnimation =
				new TranslateAnimation(0,0,0,0, TranslateAnimation.RELATIVE_TO_PARENT, 1f,
						TranslateAnimation.RELATIVE_TO_SELF, 0f);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0f,1f);
		mInAnimSet.addAnimation(translateAnimation);
		mInAnimSet.addAnimation(alphaAnimation);
		mInAnimSet.setDuration(mNoticeAnim);

		mOutAnimSet = new AnimationSet(false);
		TranslateAnimation translateAnimation2 =
				new TranslateAnimation(0,0,0,0, TranslateAnimation.RELATIVE_TO_SELF, 0f,
						TranslateAnimation.RELATIVE_TO_PARENT, -1f);
		AlphaAnimation alphaAnimation2 = new AlphaAnimation(1f,0f);
		mOutAnimSet.addAnimation(translateAnimation2);
		mOutAnimSet.addAnimation(alphaAnimation2);
		mOutAnimSet.setDuration(DEFAULT_ANIMATION_DURATION);
	}


	/**
	 * 初始化自定义的布局
	 */
	private void bindLinearLayout() {
		mScrollTitleView = LayoutInflater.from(mContext).inflate(R.layout.scroll_notice_view, null);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		addView(mScrollTitleView, params);
		mViewFlipper = (ViewFlipper) mScrollTitleView.findViewById(R.id.vf_scrollNoticeTitle);
		mViewFlipper.setInAnimation(mInAnimSet);
		mViewFlipper.setOutAnimation(mOutAnimSet);
		mViewFlipper.setFlipInterval(mNoticeDuration);
		mViewFlipper.startFlipping();
	}

	/**
	 * 设置轮播内容
	 * @param list
	 */
	public void setNoticeList(List<String> list){
		if (list ==null || list.size() ==0) return;
		mViewFlipper.removeAllViews();
		mNoticeList = new ArrayList<>();
		for (String s:list){
			if (TextUtils.isEmpty(s)) continue;
			TextView textView = new TextView(mContext);
			textView.setText(s);
			textView.setTextColor(mTextColor);
			textView.setTextSize(mTextSize);
			mNoticeList.add(s);
			LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			mViewFlipper.addView(textView, layoutParams);
		}
	}

	public String getText(){
		return ((TextView)mViewFlipper.getCurrentView()).getText().toString();
	}
}
