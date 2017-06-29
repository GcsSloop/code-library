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
 * Last modified 2017-06-01 19:57:56
 *
 * GitHub: https://github.com/GcsSloop
 * WeiBo: http://weibo.com/GcsSloop
 * WebSite: http://www.gcssloop.com
 */

package com.gcssloop.viewsupport;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class GestureUtils {

    /**
     * 手势检测
     *
     * @param view     需要检测手势的View
     * @param listener 手势监听器
     */
    public static void gestureCheck(final View view,
                                    final GestureDetector.OnGestureListener listener) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                return new GestureDetector(view.getContext(), listener).onTouchEvent(event);
            }
        });
    }
}
