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
 * Last modified 2017-04-23 00:48:39
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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作用：捕获 Crash 和莫名的异常信息
 * 作者：GcsSloop
 * 所需权限：
 * 请注意添加SD卡读写权限
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 * 相关信息：
 * Crash 信息优先保存在外部存储 /sdcard/Android/data/<包名>/files/crash/object
 * 如果 SDCard 没有挂载或者无法读写则保存在 /data/data/<包名>/files/crash/object
 * Crash 信息保存在应用文件目录，方便卸载时清除。
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private CrashInfoManager mCrashInfoManager;

    private String[] packages;

    // 系统默认的异常处理（默认情况下，系统会终止当前的异常程序）
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

    private Context mContext;
    private CrashListener mListener;

    @SuppressLint("StaticFieldLeak")
    private static CrashHandler sInstance = new CrashHandler();

    // 构造方法私有，防止外部构造多个实例，即采用单例模式
    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return sInstance;
    }

    // 这里主要完成初始化工作
    public void init(Context context) {
        // 获取系统默认的异常处理器
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 将当前实例设为系统默认的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        // 获取Context，方便内部使用
        mContext = context.getApplicationContext();
        // 创建异常信息管理器
        mCrashInfoManager = new CrashInfoManager(context);
    }

    /**
     * 这个是最关键的函数，当程序中有未被捕获的异常，
     * 系统将会自动调用 {@link #uncaughtException(Thread, Throwable)} 这个方法
     * thread 为出现未捕获异常的线程，ex为未捕获的异常，有了这个ex，我们就可以得到异常信息。
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // 如果处理失败同时默认异常处理器存在，则交予默认处理
        if (!handleException(ex) && mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(thread, ex);
        } else {
            // 跳转到异常处理页面
            jumpToErrorPage();
            // 结束当前进程
            Process.killProcess(Process.myPid());
        }
    }

    /**
     * 跳转到异常处理页面
     */
    private void jumpToErrorPage() {
        if (null != mListener) {
            Class clazz = mListener.openActivity();
            if (null != clazz) {
                Intent intent = new Intent();
                intent.setClass(mContext, clazz);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(intent);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.e(TAG, "error : ", e);
                }
            }
        }
    }

    /**
     * 处理异常信息
     * 1. 封装成实体类
     * 2. 上传异常
     * 3. 写入文件(方便后续获取)
     *
     * @param ex 异常
     * @return true 表示处理成功，false 表示没有处理或处理失败
     */
    private boolean handleException(Throwable ex) {
        Log.e(TAG, "handleException");
        if (null == ex) {
            return false;
        }

        // 表示已经发生 Crash
        if (null != mListener) {
            mListener.haveCrash();
        }

        // 信息打包
        CrashInfo info = new CrashInfo();
        try {
            packCrashTime(info);
            packDeviceInfo(info);
            packException(info, ex);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "pack crash failed!");
            e.printStackTrace();
            return false;
        }

        // 保存异常
        saveToDevice(info);

        // 上传异常
        if (null != mListener) {
            mListener.uploadCrash(info, mCrashInfoManager);
        }

        // 打印出当前调用栈信息
        ex.printStackTrace();

        return true;
    }

    /**
     * 打包异常发生的时间
     *
     * @param info CrashInfo
     */
    private void packCrashTime(CrashInfo info) {
        Log.e(TAG, "packCrashTime");
        long current = System.currentTimeMillis();
        @SuppressLint("SimpleDateFormat")
        String time = new SimpleDateFormat("yyyy-MM-dd HHmmss").format(new Date(current));
        info.setTime(time);
    }

    /**
     * 打包设备信息(包括当前应用信息)
     *
     * @param info CrashInfo
     */
    private void packDeviceInfo(CrashInfo info) throws PackageManager.NameNotFoundException {
        Log.e(TAG, "packDeviceInfo");
        // 应用的版本名称和版本号
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager
                .GET_ACTIVITIES);
        // 当前应用版本号
        info.setAppVersionName(pi.versionName);
        info.setAppVersionCode(pi.versionCode);

        // android版本号
        info.setOsVersionName(Build.VERSION.RELEASE);
        info.setOsVersionCode(Build.VERSION.SDK_INT);

        // 设备制造商
        info.setVendor(Build.MANUFACTURER);

        // 设备型号
        info.setModel(Build.MODEL);

        // cpu架构
        info.setCpuAbi(Build.CPU_ABI);
    }

    /**
     * 将异常信息打包
     *
     * @param info CrashInfo
     * @param ex   异常
     */
    private void packException(CrashInfo info, Throwable ex) {
        Log.e(TAG, "packException");
        // 异常的名称
        info.setExceptionType(ex.getClass().getSimpleName());
        info.setExceptionInfo(ex.getMessage());

        // 堆栈信息
        String trace = "\n";
        String lines = "\n";
        trace = trace + "cause by " + ex.getClass().getName() + "('" + ex.getMessage() + "')";
        StackTraceElement[] elements = ex.getStackTrace();
        for (StackTraceElement e : elements) {
            trace = trace + "\nat " + e;
            String name = e.getClassName();
            if (null == packages || packages.length < 1)
                continue;
            for (String packgae : packages) {
                if (name.startsWith(packgae)) {
                    // 保存自己写的方法中的触发位置
                    lines = lines + name + ":" + e.getLineNumber() + "\n";
                    break;
                }
            }
        }
        // 保存全部堆栈信息
        info.setExceptionTrace(trace);
        info.setExceptionLines(lines);
    }

    /**
     * 保存异常信息到设备
     *
     * @param info CrashInfo
     */
    private void saveToDevice(CrashInfo info) {
        if (null != mCrashInfoManager) {
            mCrashInfoManager.saveCrashInfo(info);
        }
    }

    //--- 可能需要应用处理的部分 -----------------------------------------------------------------------

    /**
     * 设置需要自己应用的包名，一般写前面的部分的就行了。
     * 设置后，这一部分发生的异常信息会被打包进 CrashInfo 的 ExceptionLines 中。
     *
     * @param packages 包名，如：["com.gcssloop", "com.sloop"]
     */
    public void setPackages(String[] packages) {
        this.packages = packages;
    }

    /**
     * 获取缓存器，请在 init 之后调用
     *
     * @return CrashCache
     */
    @Nullable
    public CrashInfoManager getCrashInfoManager() {
        return mCrashInfoManager;
    }

    /**
     * 设置异常监听器
     *
     * @param listener 监听器
     */
    public void setListener(CrashListener listener) {
        mListener = listener;
    }

    public interface CrashListener {
        /**
         * 通知发生了异常，在一开始调用
         */
        void haveCrash();

        /**
         * 跳转到错误页面到默认处理方案，需要返回一个 Activity
         *
         * @return XxxActivity.class
         */
        Class<? extends Activity> openActivity();

        /**
         * 上传错误信息，可以使用 Gson 等工具将 CrashInfo 转换成为 Json 数据后上传。
         * 假如说上传完成后不需要本地保存到话，可以使用 CrashInfoManager 将本地保存到数据删除，
         * 例如： manager.removeCrashInfo(info)
         *
         * @param info 包装后的错误信息
         */
        void uploadCrash(CrashInfo info, CrashInfoManager manager);
    }
}
