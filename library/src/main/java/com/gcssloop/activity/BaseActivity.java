package com.gcssloop.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.List;

/**
 * UI界面基类
 * Author: Sloop
 * Version: v1.0
 * Date: 2015/11/12
 * <ul type="disc">
 * <li><a href="http://www.sloop.icoc.cc"    target="_blank">作者网站</a>      <br/></li>
 * <li><a href="http://weibo.com/5459430586" target="_blank">作者微博</a>      <br/></li>
 * <li><a href="https://github.com/GcsSloop" target="_blank">作者GitHub</a>   <br/></li>
 * </ul>
 */
public class BaseActivity extends AppCompatActivity {

    private Toast mToast = null;
    private Message mMessage = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * 发出一个短Toast
     *
     * @param text 内容
     */
    public void toastShort(String text) {
        toast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 发出一个长toast提醒
     *
     * @param text 内容
     */
    public void toastLong(String text) {
        toast(text, Toast.LENGTH_LONG);
    }


    private void toast(final String text, final int duration) {
        if (!TextUtils.isEmpty(text)) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mToast == null) {
                        mToast = Toast.makeText(getApplicationContext(), text, duration);
                    } else {
                        mToast.setText(text);
                        mToast.setDuration(duration);
                    }
                    mToast.show();
                }
            });
        }
    }

    /**
     * 向Handler发消息
     *
     * @param what     消息类型
     * @param message  消息内容
     * @param mHandler 发到哪个Handler
     */
    public void sendMsgToHandler(int what, Object message, Handler mHandler) {
        if (mMessage == null) {
            mMessage = Message.obtain();
        }
        mMessage.what = what;
        mMessage.obj = message;
        mHandler.sendMessage(mMessage);
    }

    /**
     * 打开Activity
     *
     * @param cls class
     */
    protected void openActivity(Class<?> cls) {
        openActivity(this, cls);
        //    overridePendingTransition(R.anim.zoomin_back, R.anim.zoomout_back);
    }

    /**
     * 打开Activity
     *
     * @param context 上下文
     * @param cls     class
     */
    public static void openActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    @Override
    public void finish() {
        super.finish();
        //    overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
    }

    /**
     * 判断程序是否处于后台
     *
     * @param context 上下文
     * @return true表示程序当前处于后台，false表示程序当前处于前台
     */
    public static boolean isBackground(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
