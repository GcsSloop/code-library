package com.gcssloop.activity;

import android.os.Bundle;
import android.os.Handler;

import com.sloop.football.R;

/**
 * 应用首屏
 * Author: Sloop
 * Version: v1.0
 * Date: 2015/11/12
 */
public class SplashActivity extends BaseActivity {

    private boolean isFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 闪屏的核心代码
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFinish)
                    return;
                openActivity(MainActivity.class);  //从启动动画ui跳转到主ui
                SplashActivity.this.finish();    // 结束启动动画界面

            }
        }, 2000);    //启动动画持续2秒钟
    }

    @Override
    protected void onStop() {
        super.onStop();
        isFinish = true;

    }
}
