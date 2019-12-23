// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AppInfo.java

package com.example.faq.ueditor.define;

import java.util.HashMap;
import java.util.Map;

public final class AppInfo {

    public AppInfo() {
    }

    public static String getStateInfo(int key) {
        return (String) info.get(Integer.valueOf(key));
    }

    public static final int SUCCESS = 0;
    public static final int MAX_SIZE = 1;
    public static final int PERMISSION_DENIED = 2;
    public static final int FAILED_CREATE_FILE = 3;
    public static final int IO_ERROR = 4;
    public static final int NOT_MULTIPART_CONTENT = 5;
    public static final int PARSE_REQUEST_ERROR = 6;
    public static final int NOTFOUND_UPLOAD_DATA = 7;
    public static final int NOT_ALLOW_FILE_TYPE = 8;
    public static final int INVALID_ACTION = 101;
    public static final int CONFIG_ERROR = 102;
    public static final int PREVENT_HOST = 201;
    public static final int CONNECTION_ERROR = 202;
    public static final int REMOTE_FAIL = 203;
    public static final int NOT_DIRECTORY = 301;
    public static final int NOT_EXIST = 302;
    public static final int ILLEGAL = 401;
    public static Map info = new HashMap() {


        {
            put(Integer.valueOf(0), "SUCCESS");
            put(Integer.valueOf(101), "\u65E0\u6548\u7684Action");
            put(Integer.valueOf(102), "\u914D\u7F6E\u6587\u4EF6\u521D\u59CB\u5316\u5931\u8D25");
            put(Integer.valueOf(203), "\u6293\u53D6\u8FDC\u7A0B\u56FE\u7247\u5931\u8D25");
            put(Integer.valueOf(201), "\u88AB\u963B\u6B62\u7684\u8FDC\u7A0B\u4E3B\u673A");
            put(Integer.valueOf(202), "\u8FDC\u7A0B\u8FDE\u63A5\u51FA\u9519");
            put(Integer.valueOf(1), "\u6587\u4EF6\u5927\u5C0F\u8D85\u51FA\u9650\u5236");
            put(Integer.valueOf(2), "\u6743\u9650\u4E0D\u8DB3");
            put(Integer.valueOf(3), "\u521B\u5EFA\u6587\u4EF6\u5931\u8D25");
            put(Integer.valueOf(4), "IO\u9519\u8BEF");
            put(Integer.valueOf(5), "\u4E0A\u4F20\u8868\u5355\u4E0D\u662Fmultipart/form-data\u7C7B\u578B");
            put(Integer.valueOf(6), "\u89E3\u6790\u4E0A\u4F20\u8868\u5355\u9519\u8BEF");
            put(Integer.valueOf(7), "\u672A\u627E\u5230\u4E0A\u4F20\u6570\u636E");
            put(Integer.valueOf(8), "\u4E0D\u5141\u8BB8\u7684\u6587\u4EF6\u7C7B\u578B");
            put(Integer.valueOf(301), "\u6307\u5B9A\u8DEF\u5F84\u4E0D\u662F\u76EE\u5F55");
            put(Integer.valueOf(302), "\u6307\u5B9A\u8DEF\u5F84\u5E76\u4E0D\u5B58\u5728");
            put(Integer.valueOf(401), "Callback\u53C2\u6570\u540D\u4E0D\u5408\u6CD5");
        }
    };

}
