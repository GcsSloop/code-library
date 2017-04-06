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
 * Last modified 2017-04-05 15:38:44
 *
 * GitHub: https://github.com/GcsSloop
 * WeiBo: http://weibo.com/GcsSloop
 * WebSite: http://www.gcssloop.com
 */

package com.gcssloop.customview.utils;

import android.graphics.Point;
import android.graphics.PointF;

public class MathUtils {

    public MathUtils() {
    }

    /**
     * Get the distance between two points.
     *
     * @param A Point A
     * @param B Point B
     * @return the distance between point A and point B.
     */
    public static int getDistance(PointF A, PointF B) {
        return (int) Math.sqrt(Math.pow(A.x - B.x, 2) + Math.pow(A.y - B.y, 2));
    }

    /**
     * Get the distance between two points.
     */
    public static int getDistance(float x1, float y1, float x2, float y2) {
        return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /**
     * Get the coordinates of a point on the line by cut length.
     *
     * @param A         Point A
     * @param B         Point B
     * @param cutLength cut length
     * @return the point.
     */
    public static Point getPointByCutLength(Point A, Point B, int cutLength) {
        float radian = getRadian(A, B);
        return new Point(A.x + (int) (cutLength * Math.cos(radian)), A.y + (int) (cutLength * Math.sin(radian)));
    }

    /**
     * Get the radian between current line(determined by point A and B) and horizontal line.
     *
     * @param A point A
     * @param B point B
     * @return the radian
     */
    public static float getRadian(Point A, Point B) {
        float lenA = B.x - A.x;
        float lenB = B.y - A.y;
        float lenC = (float) Math.sqrt(lenA * lenA + lenB * lenB);
        float radian = (float) Math.acos(lenA / lenC);
        radian = radian * (B.y < A.y ? -1 : 1);
        return radian;
    }


    /**
     * angle to radian
     *
     * @param angle angle
     * @return radian
     */
    public static double angle2Radian(double angle) {
        return angle / 180 * Math.PI;
    }

    /**
     * radian to angle
     *
     * @param radian radian
     * @return angle
     */
    public static double radian2Angle(double radian) {
        return radian / Math.PI * 180;
    }
}
