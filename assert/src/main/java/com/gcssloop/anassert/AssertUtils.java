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
 * Last modified 2017-04-26 01:32:57
 *
 * GitHub: https://github.com/GcsSloop
 * WeiBo: http://weibo.com/GcsSloop
 * WebSite: http://www.gcssloop.com
 */

package com.gcssloop.anassert;

import android.os.Looper;

/**
 * 断言工具
 */
public class AssertUtils {

    /**
     * 断言对象不为null
     *
     * @param obj 数据
     */
    public static void assertNotNull(Object obj) {
        if (null == obj) {
            throw new GcsNullException("Not allowed to be null!");
        }
    }

    /**
     * 断言对象不为空
     *
     * @param obj 对象
     * @param msg 说明
     */
    public static void assertNotNull(Object obj, String msg) {
        if (null == obj) {
            throw new GcsNullException(msg);
        }
    }

    /**
     * 断言在主线程
     */
    public static void assertInMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException("This is not in main thread!");
        }
    }

    /**
     * 断言在子线程
     */
    public static void assertInBackground() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("This is not in background thread!");
        }
    }
}
