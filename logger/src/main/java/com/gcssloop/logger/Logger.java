
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
 * Last modified 2017-06-29 13:58:49
 *
 * GitHub: https://github.com/GcsSloop
 * WeiBo: http://weibo.com/GcsSloop
 * WebSite: http://www.gcssloop.com
 */

package com.gcssloop.logger;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Logger {

    /**
     * 被屏蔽的TAG
     */
    private static ArrayList<String> mMaskedTag = new ArrayList<>();

    private static List<String> mMaskedClass = new ArrayList<>();

    /**
     * 屏蔽当前类
     *
     * @param isBlock 是否屏蔽
     */
    public static void maskThis(Boolean isBlock) {
        if (isBlock) {
            mMaskedClass.add(getClassName(2));
        } else {
            mMaskedClass.remove(getClassName(2));
        }
    }

    private static String DEFAULT_TAG = "";

    private static Config mConfig;

    private Logger() {
    }

    public static Config init() {
        mConfig = new Config(DEFAULT_TAG);
        mMaskedTag.clear();
        return mConfig;
    }

    public static Config init(@NonNull String tag) {
        mConfig = new Config(tag);
        mMaskedTag.clear();
        return mConfig;
    }

    /**
     * 添加被屏蔽的 tag
     *
     * @param tag tag
     */
    public static void addMaskedTag(String tag) {
        mMaskedTag.add(tag);
    }

    /**
     * 移除被屏蔽的 tag
     *
     * @param tag tag
     */
    public static void removeMaskedTag(String tag) {
        mMaskedTag.remove(tag);
    }

    /**
     * 添加一堆需要屏蔽的 tag
     *
     * @param tags tags
     */
    public static void addMaskedTags(List<String> tags) {
        mMaskedTag.addAll(tags);
    }

    /**
     * 移除一堆需要被屏蔽的 tag
     *
     * @param tags tags
     */
    public static void removeMaskedTags(List<String> tags) {
        mMaskedTag.removeAll(tags);
    }

    public static void v(String message) {
        log(Config.LEVEL_VERBOSE, mConfig.getTag(), message);
    }

    public static void d(String message) {
        log(Config.LEVEL_DEBUG, mConfig.getTag(), message);
    }

    public static void i(String message) {
        log(Config.LEVEL_INFO, mConfig.getTag(), message);
    }

    public static void w(String message) {
        log(Config.LEVEL_WARN, mConfig.getTag(), message);
    }

    public static void e(String message) {
        log(Config.LEVEL_ERROR, mConfig.getTag(), message);
    }

    public static void v(String tag, String message) {
        log(Config.LEVEL_VERBOSE, tag, message);
    }

    public static void d(String tag, String message) {
        log(Config.LEVEL_DEBUG, tag, message);
    }

    public static void i(String tag, String message) {
        log(Config.LEVEL_INFO, tag, message);
    }

    public static void w(String tag, String message) {
        log(Config.LEVEL_WARN, tag, message);
    }

    public static void e(String tag, String message) {
        log(Config.LEVEL_ERROR, tag, message);
    }

    private static void log(int level, String tag, String message) {
        if (mConfig.getLevel() == Config.LEVEL_NONE) {
            return;
        }

        if (mMaskedTag.contains(tag)) {
            return;
        }

        String className = getClassName(3);
        if (mMaskedClass.contains(className)) {
            return;
        }

        int index = className.indexOf("$");
        if (index >= 0) {
            String simpleName = className.substring(0, index);
            if (mMaskedClass.contains(simpleName)) {
                return;
            }
        }

        if (level < mConfig.getLevel()) {
            return;
        }

        switch (level) {
            case Config.LEVEL_VERBOSE:
                Log.v(tag, message);
                break;
            case Config.LEVEL_DEBUG:
                Log.d(tag, message);
                break;
            case Config.LEVEL_INFO:
                Log.i(tag, message);
                break;
            case Config.LEVEL_WARN:
                Log.w(tag, message);
                break;
            case Config.LEVEL_ERROR:
                Log.e(tag, message);
                break;
        }
    }


    /**
     * 获取类名
     *
     * @param level 层级
     * @return 调用者类名
     */
    private static String getClassName(int level) {
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[level];
        String result = thisMethodStack.getClassName();
        int lastIndex = result.lastIndexOf(".");
        result = result.substring(lastIndex + 1, result.length());
        return result;
    }
}
