/**
 * @Title: MenuItem.java
 * @Package com.sloop.view
 * @Copyright: Copyright (c) 2015
 * 
 * @author sloop
 * @date 2015年6月28日 下午10:13:05
 * @version V1.0
 */

package com.gcssloop.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gcssloop.library.R;


/**
 * 菜单条目
 * 
 * @ClassName: MenuItem
 * @author: sloop
 * @website: http://www.sloop.icoc.cc
 * @weibo: http://weibo.com/u/5459430586
 * @date 2015年6月28日 下午10:13:05
 *
 * 有 attr 文件
 */

public class MenuItem extends RelativeLayout {

	private final static int ICON_ID = 2015;

	private String itemText;					//条目文字
	private int itemTextColor;					//文字颜色
	private float itemTextSize;					//文字大小
	private Drawable leftImage, rightImage;		//左右图片

	private ImageView leftImageView, rightImageView;
	private TextView itemTextView;

	//布局属性
	private LayoutParams leftParams, rightParams, itemParams;

	public MenuItem(Context context, AttributeSet attrs){
		super(context, attrs);
		int margin = dip2px((ContextThemeWrapper) context, 5);

		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MenuItem);

		//取出自定义属性 - 标题
		itemText = ta.getString(R.styleable.MenuItem_ItemText);
		itemTextSize = ta.getDimension(R.styleable.MenuItem_ItemTextSize, 16);
		itemTextSize = px2dip((ContextThemeWrapper) context, itemTextSize);
		itemTextColor = ta.getColor(R.styleable.MenuItem_ItemTextColor, Color.BLACK);

		//取出自定义属性 - 左右图标
		leftImage = ta.getDrawable(R.styleable.MenuItem_LeftImage);
		rightImage = ta.getDrawable(R.styleable.MenuItem_RightImage);

		//回收TypedArray（避免浪费资源，避免因为缓存导致的错误）
		ta.recycle();

		//创建对象
		itemTextView = new TextView(context);
		leftImageView = new ImageView(context);
		rightImageView = new ImageView(context);

		//设置属性
		leftImageView.setImageDrawable(leftImage);
		leftImageView.setId(ICON_ID);
		rightImageView.setImageDrawable(rightImage);
		itemTextView.setText(itemText);
		itemTextView.setTextSize(itemTextSize);
		itemTextView.setTextColor(itemTextColor);
		itemTextView.setGravity(Gravity.CENTER);

		//左侧布局
		leftParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		leftParams.setMargins(margin, margin, margin, margin);
		leftParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
		leftParams.width=dip2px((ContextThemeWrapper) context,30);
		leftParams.leftMargin = dip2px((ContextThemeWrapper) context,20);
		addView(leftImageView, leftParams);

		//右侧布局
		rightParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		rightParams.setMargins(margin, margin, margin, margin);
		rightParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
		rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
		addView(rightImageView, rightParams);

		//item标题布局
		itemParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		itemParams.setMargins(margin, margin, margin, margin);
		itemParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
		itemParams.addRule(RelativeLayout.RIGHT_OF, ICON_ID);
		addView(itemTextView, itemParams);

	}
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(ContextThemeWrapper context, float dpValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(ContextThemeWrapper context, float pxValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
