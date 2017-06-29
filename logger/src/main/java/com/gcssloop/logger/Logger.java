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
 * Last modified 2017-04-05 15:35:24
 *
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

    private static String DEFAULT_TAG = "GCS-LOG";

    private static Config mConfig;

    private Logger() {

    }

    public static Config init() {
        mConfig = new Config(DEFAULT_TAG);
        return mConfig;
    }

    public static Config init(@NonNull String tag) {
        mConfig = new Config(tag);
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
    public void removeMaskedTag(String tag) {
        mMaskedTag.remove(tag);
    }

    /**
     * 添加一堆需要屏蔽的 tag
     *
     * @param tags tags
     */
    public void addMaskedTags(List<String> tags) {
        mMaskedTag.addAll(tags);
    }

    /**
     * 移除一堆需要被屏蔽的 tag
     *
     * @param tags tags
     */
    public void removeMaskedTags(List<String> tags) {
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
}
