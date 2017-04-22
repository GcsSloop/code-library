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


import java.io.Serializable;

public class CrashInfo implements Serializable {
    private String Time;            // 异常发生时间
    private String AppVersionName;  // 应用版本名称
    private Integer AppVersionCode; // 应用版本号
    private String OsVersionName;   // Android 系统版本名称
    private Integer OsVersionCode;  // Android 系统版本号
    private String Vendor;          // 制造商
    private String Model;           // 型号
    private String CpuAbi;          // CPU 架构
    private String ExceptionType;   // 异常类型
    private String ExceptionInfo;   // 异常信息
    private String ExceptionLines;  // 引起异常的位置(自己包下的)
    private String ExceptionTrace;  // 异常堆栈信息

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getAppVersionName() {
        return AppVersionName;
    }

    public void setAppVersionName(String appVersionName) {
        AppVersionName = appVersionName;
    }

    public Integer getAppVersionCode() {
        return AppVersionCode;
    }

    public void setAppVersionCode(Integer appVersionCode) {
        AppVersionCode = appVersionCode;
    }

    public String getOsVersionName() {
        return OsVersionName;
    }

    public void setOsVersionName(String osVersionName) {
        OsVersionName = osVersionName;
    }

    public Integer getOsVersionCode() {
        return OsVersionCode;
    }

    public void setOsVersionCode(Integer osVersionCode) {
        OsVersionCode = osVersionCode;
    }

    public String getVendor() {
        return Vendor;
    }

    public void setVendor(String vendor) {
        Vendor = vendor;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getCpuAbi() {
        return CpuAbi;
    }

    public void setCpuAbi(String cpuAbi) {
        CpuAbi = cpuAbi;
    }

    public String getExceptionType() {
        return ExceptionType;
    }

    public void setExceptionType(String exceptionType) {
        ExceptionType = exceptionType;
    }

    public String getExceptionInfo() {
        return ExceptionInfo;
    }

    public void setExceptionInfo(String exceptionInfo) {
        ExceptionInfo = exceptionInfo;
    }

    public String getExceptionLines() {
        return ExceptionLines;
    }

    public void setExceptionLines(String exceptionLines) {
        ExceptionLines = exceptionLines;
    }

    public String getExceptionTrace() {
        return ExceptionTrace;
    }

    public void setExceptionTrace(String exceptionTrace) {
        ExceptionTrace = exceptionTrace;
    }

    @Override public String toString() {
        return "CrashInfo:" + "\n" +
                "Time='" + Time + "'\n" +
                "AppVersionName='" + AppVersionName + "'\n" +
                "AppVersionCode='" + AppVersionCode + "'\n" +
                "OsVersionName='" + OsVersionName + "'\n" +
                "OsVersionCode='" + OsVersionCode + "'\n" +
                "Vendor='" + Vendor + "'\n" +
                "Model='" + Model + "'\n" +
                "CpuAbi='" + CpuAbi + "'\n\n" +
                "ExceptionType='" + ExceptionType + "'\n" +
                "ExceptionInfo='" + ExceptionInfo + "'\n\n" +
                "ExceptionLines=" + ExceptionLines + "\n\n" +
                "ExceptionTrace=" + ExceptionTrace;
    }
}
