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
 * Last modified 2017-08-23 10:22:27
 *
 * GitHub: https://github.com/GcsSloop
 * WeiBo: http://weibo.com/GcsSloop
 * WebSite: http://www.gcssloop.com
 */

package com.gcssloop.toast;

import android.content.Context;
import android.widget.Toast;

/**
 * 作用：Toast工具
 * 作者：GcsSloop
 * 摘要：
 * 防止重复点击导致Toast长时间显示而不消失。
 */
public class ToastUtils {
    private static Toast toast;

    public static void showShort(Context context, String content) {
        show(context, content, Toast.LENGTH_SHORT);
    }

    public static void showLong(Context context, String content) {
        show(context, content, Toast.LENGTH_LONG);
    }

    public static void show(Context context, String content, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, content, duration);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
