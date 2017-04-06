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
 * Last modified 2017-04-06 21:50:05
 *
 *  GitHub: https://github.com/GcsSloop
 *  WeiBo: http://weibo.com/GcsSloop
 *  WebSite: http://www.gcssloop.com
 */

package com.gcssloop.idcard;

/**
 * 身份证信息
 */
public class IDInfo {
    private Boolean isOk = false;   // 是否正确
    private String errorInfo = "";   // 错误信息
    private String cardId = "";     // 身份证号
    private String year = "";       // 年
    private String month = "";      // 月
    private String day = "";        // 日
    private int provinceCode = -1;  // 省份代码

    public Boolean getOk() {
        return isOk;
    }

    public void setOk(Boolean ok) {
        isOk = ok;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        isOk = false;
        this.errorInfo = errorInfo;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvince() {
        return Province.getProvinceById(provinceCode);
    }

    @Override
    public String toString() {
        return "IDInfo{" +
                "isOk=" + isOk +
                ", errorInfo='" + errorInfo + '\'' +
                ", cardId='" + cardId + '\'' +
                ", year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                ", provinceCode=" + provinceCode +
                ", province=" + Province.getProvinceById(provinceCode) +
                '}';
    }

    /**
     * 省份信息
     */
    public static class Province {


        public static String getProvinceById(Integer id) {
            switch (id) {
                case 11: return "北京";
                case 12: return "天津";
                case 13: return "河北";
                case 14: return "山西";
                case 15: return "内蒙古";
                case 21: return "辽宁";
                case 22: return "吉林";
                case 23: return "黑龙江";
                case 31: return "上海";
                case 32: return "江苏";
                case 33: return "浙江";
                case 34: return "安徽";
                case 35: return "福建";
                case 36: return "江西";
                case 37: return "山东";
                case 41: return "河南";
                case 42: return "湖北";
                case 43: return "湖南";
                case 44: return "广东";
                case 45: return "广西";
                case 46: return "海南";
                case 50: return "重庆";
                case 51: return "四川";
                case 52: return "贵州";
                case 53: return "云南";
                case 54: return "西藏";
                case 61: return "陕西";
                case 62: return "甘肃";
                case 63: return "青海";
                case 64: return "宁夏";
                case 65: return "新疆";
                case 71: return "台湾";
                case 81: return "香港";
                case 82: return "澳门";
                case 91: return "国外";
                default: return "";
            }
        }
    }
}
