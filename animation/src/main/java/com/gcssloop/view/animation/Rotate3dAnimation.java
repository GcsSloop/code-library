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
 * Last modified 2017-04-07 01:18:20
 *
 *  GitHub: https://github.com/GcsSloop
 *  WeiBo: http://weibo.com/GcsSloop
 *  WebSite: http://www.gcssloop.com
 */

package com.gcssloop.view.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.ContextThemeWrapper;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Rotate3dAnimation extends Animation {

    private final float mFromDegrees;           // 开始角度
    private final float mToDegrees;             // 结束角度
    private final float mCenterX, mCenterY;     // 中心点
    private final float mDepthZ;                // 深度
    private final boolean mReverse;             // 是否需要扭曲
    private Camera mCamera;                     // 摄像头
    ContextThemeWrapper context;                // 上下文
    float scale = 1;                            // 屏幕密度（默认值为1）

    /**
     * 创建一个新的实例 Rotate3dAnimation.
     *
     * @param fromDegrees 开始角度
     * @param toDegrees   结束角度
     * @param centerX     中心点x坐标
     * @param centerY     中心点y坐标
     * @param depthZ      深度
     * @param reverse     是否扭曲
     */
    public Rotate3dAnimation(ContextThemeWrapper context, float fromDegrees, float toDegrees, float centerX, float
            centerY, float depthZ, boolean reverse) {
        this.context = context;
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mCenterX = centerX;
        mCenterY = centerY;
        mDepthZ = depthZ;
        mReverse = reverse;
        //获取手机像素比 （即dp与px的比例）
        scale = context.getResources().getDisplayMetrics().density;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {

        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    // 生成Transformation
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDegrees = mFromDegrees;
        // 生成中间角度
        float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);


        final float centerX = mCenterX;
        final float centerY = mCenterY;
        final Camera camera = mCamera;

        final Matrix matrix = t.getMatrix();

        camera.save();
        if (mReverse) {
            camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
        } else {
            camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
        }
        camera.rotateY(degrees);
        // 取得变换后的矩阵
        camera.getMatrix(matrix);
        camera.restore();
        float[] mValues = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        matrix.getValues(mValues);            //获取数值
        mValues[6] = mValues[6] / scale;      //数值修正
        matrix.setValues(mValues);            //重新赋值

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
