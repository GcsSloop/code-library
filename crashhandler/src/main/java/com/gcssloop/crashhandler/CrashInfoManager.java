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
 * Last modified 2017-04-20 22:55:59
 *
 * GitHub: https://github.com/GcsSloop
 * WeiBo: http://weibo.com/GcsSloop
 * WebSite: http://www.gcssloop.com
 */

/*
 * Create in 2017-04-20 by GcsSloop
 * Last modified 2017-04-20 16:31:38
 */

package com.gcssloop.crashhandler;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * 异常信息(CrashInfo)管理器
 */
public class CrashInfoManager {
    private String TAG = "CrashCache";

    private static String PATH;                         // log 缓存路径
    private static String FILE_NAME = "crash";          // log 文件名
    private static String FILE_NAME_SUFFIX = ".obj";    // log 文件的后缀名

    CrashInfoManager(Context context) {
        // 初始化 crash 信息保存位置，根据 SDCard 是否挂载初始化和是否能获取到SD卡位置来确定
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                PATH = context.getExternalFilesDir("").getPath() + "/crash/object/";
            } catch (NullPointerException ex) {
                PATH = context.getFilesDir().getPath() + "/crash/object/";
            }
        } else {
            PATH = context.getFilesDir().getPath() + "/crash/object/";
        }
    }

    public boolean saveCrashInfo(CrashInfo info) {
        Log.e(TAG, "saveCrashInfo");
        // 确保缓存文件夹存在
        assureCacheDir();
        //以当前时间创建 log 文件
        File file = new File(PATH + FILE_NAME + info.getTime() + FILE_NAME_SUFFIX);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG, file.getAbsolutePath().toString());
        // 将 CrashInfo 转换数据存储起来(序列化)
        saveSerializableToFile(file, info);
        Log.e(TAG, "save crash info success.");
        return true;
    }

    // 确保缓存文件夹存在
    private void assureCacheDir() {
        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 获取所有的异常
     *
     * @return 所有的异常，ArrayList<CrashInfo>
     */
    public ArrayList<CrashInfo> getAllCrashInfo() {
        File dir = new File(PATH);
        if (!dir.exists())
            return null;
        ArrayList<CrashInfo> crashInfos = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            CrashInfo info = getSerializableFromFile(file);
            crashInfos.add(info);
        }
        return crashInfos;
    }

    /**
     * 移除单个异常信息
     *
     * @param info CrashInfo
     * @return 是否移除
     */
    public boolean removeCrashInfo(CrashInfo info) {
        Log.e(TAG, "removeCrashInfo");
        if (null == info) {
            return false;
        }
        File file = new File(PATH + FILE_NAME + info.getTime() + FILE_NAME_SUFFIX);
        if (file.exists()) {
            file.delete();
        }
        return true;
    }

    /**
     * 移除所有异常信息文件
     */
    public void removeAllCrashInfo() {
        File dir = new File(PATH);
        if (!dir.exists())
            return;
        File[] files = dir.listFiles();
        for (File file : files) {
            file.delete();
        }
    }


    /**
     * 将可序列化对象写入文件
     *
     * @param file         文件
     * @param serializable 要写入的对象
     */
    private <T extends Serializable> void saveSerializableToFile(File file, T serializable) {
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(serializable);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeQuietly(oos);
            closeQuietly(fos);
        }
    }

    /**
     * 从文件中读取可序列化对象
     *
     * @param file 文件
     * @return 对象
     */
    private <T extends Serializable> T getSerializableFromFile(File file) {
        ObjectInputStream ois = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            Object object = ois.readObject();
            return (T) object;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeQuietly(fis);
            closeQuietly(ois);
        }
        return null;
    }

    /**
     * 关闭Closeable对象
     *
     * @param closeable 可关闭的对象
     */
    private void closeQuietly(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
