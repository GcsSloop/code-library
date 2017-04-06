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
 * Last modified 2017-04-06 21:47:39
 *
 *  GitHub: https://github.com/GcsSloop
 *  WeiBo: http://weibo.com/GcsSloop
 *  WebSite: http://www.gcssloop.com
 */

package com.gcssloop.idcard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 身份证 ID 解析器
 */
public class IDCardAnalysis {
    private static final String[] verifyCodes = {"1", "0", "x", "9", "8", "7", "6", "5", "4",
            "3", "2"};
    private static final String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
            "9", "10", "5", "8", "4", "2"};


    /**
     * 解析
     *
     * @param IDStr 身份证 ID
     * @return 解析后的数据
     */
    public static IDInfo analysis(String IDStr) {
        IDInfo result = new IDInfo();
        result.setCardId(IDStr);

        String Ai = "";

        // 非空检测
        if (null == IDStr || IDStr.isEmpty()) {
            result.setErrorInfo("传入数值不能为空。");
            return result;
        }

        // 长度验证
        if (IDStr.length() != 18 && IDStr.length() != 15) {
            result.setErrorInfo("身份证长度应该是 15 位或者 18 位。");
            return result;
        }

        // 数字验证，除最后一位之外全部为数字
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumber(Ai) == false) {
            result.setErrorInfo("身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。");
            return result;
        }

        // 生日验证
        String strYear = Ai.substring(6, 10);// 年份 
        String strMonth = Ai.substring(10, 12);// 月份 
        String strDay = Ai.substring(12, 14);// 月份 
        String strBirthDay = strYear + "-" + strMonth + "-" + strDay;
        if (isValidBirthdayDate(strBirthDay) == false) {
            result.setErrorInfo("身份证生日无效。");
            return result;
        }
        result.setYear(strYear);
        result.setMonth(strMonth);
        result.setDay(strDay);

        // 地区验证
        Integer provinceCode = Integer.parseInt(Ai.substring(0, 2));
        if (IDInfo.Province.getProvinceById(provinceCode).isEmpty()) {
            result.setErrorInfo("身份证地区编码错误。");
            return result;
        }
        result.setProvinceCode(provinceCode);

        // 最后一位验证
        if (IDStr.length() == 18) {
            int TotalMul = 0;
            for (int i = 0; i < 17; i++) {
                TotalMul = TotalMul
                        + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                        * Integer.parseInt(Wi[i]);
            }
            int modValue = TotalMul % 11;
            String strVerifyCode = verifyCodes[modValue];
            Ai = Ai + strVerifyCode;

            if (Ai.equals(IDStr) == false) {
                result.setErrorInfo("最后一位校验失败。");
                return result;
            }
        }

        result.setOk(true);
        return result;

    }

    // 是否是有效的生日地址
    private static boolean isValidBirthdayDate(String date) {
        if (date == null || date.isEmpty())
            return false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (date.trim().length() != dateFormat.toPattern().length())
            return false;

        dateFormat.setLenient(false);

        try {
            // 生日超过当前日期，判断无效
            GregorianCalendar gc = new GregorianCalendar();
            if (gc.getTime().getTime() - dateFormat.parse(date.trim()).getTime() < 0) {
                return false;
            }
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    // 是否全是数字
    private static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
