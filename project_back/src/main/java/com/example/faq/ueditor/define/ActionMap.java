// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ActionMap.java

package com.example.faq.ueditor.define;

import java.util.HashMap;
import java.util.Map;

public final class ActionMap {

    public ActionMap() {
    }

    public static int getType(String key) {
        return ((Integer) mapping.get(key)).intValue();
    }

    public static final Map mapping = new HashMap() {


        {
            put("config", Integer.valueOf(0));
            put("uploadimage", Integer.valueOf(1));
            put("uploadscrawl", Integer.valueOf(2));
            put("uploadvideo", Integer.valueOf(3));
            put("uploadfile", Integer.valueOf(4));
            put("catchimage", Integer.valueOf(5));
            put("listfile", Integer.valueOf(6));
            put("listimage", Integer.valueOf(7));
        }
    };
    public static final int CONFIG = 0;
    public static final int UPLOAD_IMAGE = 1;
    public static final int UPLOAD_SCRAWL = 2;
    public static final int UPLOAD_VIDEO = 3;
    public static final int UPLOAD_FILE = 4;
    public static final int CATCH_IMAGE = 5;
    public static final int LIST_FILE = 6;
    public static final int LIST_IMAGE = 7;

}
