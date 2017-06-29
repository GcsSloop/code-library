

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

public class Config {
    public static final int LEVEL_NONE = 0;
    public static final int LEVEL_FULL = 1;

    public static final int LEVEL_VERBOSE = 2;
    public static final int LEVEL_DEBUG = 3;
    public static final int LEVEL_INFO = 4;
    public static final int LEVEL_WARN = 5;
    public static final int LEVEL_ERROR = 6;
    public static final int LEVEL_ASSERT = 7;

    private String tag;
    private int level;

    public Config(String tag) {
        this.tag = tag;
        level = LEVEL_FULL;
    }

    public Config setLevel(@NonNull int level) {
        this.level = level;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public String getTag() {

        if (null == tag || tag.isEmpty()) {
            return getFullTag();
        }
        return tag;
    }

    /**
     * 获取默认的 tag
     *
     * @return 类名#方法名(调用位置)
     */
    private static String getFullTag() {
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[3];
        String result = thisMethodStack.getClassName();
        int lastIndex = result.lastIndexOf(".");
        String className = result.substring(lastIndex + 1, result.length());
        lastIndex = className.lastIndexOf("$");
        String simpleName = className;
        if (lastIndex > 0)
            simpleName = className.substring(0, lastIndex);

        result = className + "#" + thisMethodStack
                .getMethodName() + ":(" + simpleName + ".java:" + thisMethodStack.getLineNumber()
                + ")";
        return result;
    }
}
