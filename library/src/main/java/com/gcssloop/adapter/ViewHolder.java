package com.gcssloop.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: Sloop
 * Version: v1.0
 * Date: 2015/11/17
 * <ul type="disc">
 * <li><a href="http://www.sloop.icoc.cc"    target="_blank">作者网站</a>      </li>
 * <li><a href="http://weibo.com/5459430586" target="_blank">作者微博</a>      </li>
 * <li><a href="https://github.com/GcsSloop" target="_blank">作者GitHub</a>   </li>
 * </ul>
 */
public class ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;

    private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);  //setTag
    }

    /**
     * 获取ViewHolder的实例
     *
     * @param context     上下文
     * @param convertView 布局
     * @param parent      父布局
     * @param layoutId    布局ID
     * @param position    位置
     * @return ViewHolder实例
     */
    public static ViewHolder getInstance(Context context, View convertView, ViewGroup parent, int layoutId, int
            position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            return (ViewHolder) convertView.getTag();
        }
    }

    /**
     * 通过View的id来获取子View
     *
     * @param resId view的id
     * @param <T>   泛型
     * @return 子View
     */
    public <T extends View> T getView(int resId) {
        View view = mViews.get(resId);

        //如果该View没有缓存过，则查找View并缓存
        if (view == null) {
            view = mConvertView.findViewById(resId);
            mViews.put(resId, view);
        }

        return (T) view;
    }

    /**
     * 获取布局View
     *
     * @return 布局View
     */
    public View getConvertView() {
        return mConvertView;
    }
}