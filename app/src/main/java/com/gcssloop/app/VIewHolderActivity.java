/*
 * Copyright 2017 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2017-04-17 17:15:04
 *
 * GitHub: https://github.com/GcsSloop
 * WeiBo: http://weibo.com/GcsSloop
 * WebSite: http://www.gcssloop.com
 */

package com.gcssloop.app;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * 使用了 ViewHolder 的 Activity，损耗部分性能让 Activity 使用更方便。
 * 注意：不推荐使用 ViewHolderActivity 加载过于复制的布局。
 */
public abstract class ViewHolderActivity extends RootActivity {
    protected ViewHolder mViewHolder;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewHolder = new ViewHolder(getLayoutInflater(), null, getLayoutId());
        setContentView(mViewHolder.getRootView());
        initActionBar();
        initDatas();
        initViews(mViewHolder, mViewHolder.getRootView());
    }


    /**
     * 在此处传递当前 Activity 对应的布局
     *
     * @return 布局id，例如： R.layout.main
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 初始化数据，调用位置在 initViews 之前
     */
    protected void initDatas() {
    }

    /**
     * 初始化 View， 调用位置在 initDatas 之后
     */
    protected abstract void initViews(ViewHolder holder, View root);
}
