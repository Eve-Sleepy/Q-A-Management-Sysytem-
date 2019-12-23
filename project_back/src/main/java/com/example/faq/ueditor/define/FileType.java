// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FileType.java

package com.example.faq.ueditor.define;

import java.util.HashMap;
import java.util.Map;

public class FileType {

    public FileType() {
    }

    public static String getSuffix(String key) {
        return (String) types.get(key);
    }

    public static String getSuffixByFilename(String filename) {
        return filename.substring(filename.lastIndexOf(".")).toLowerCase();
    }

    public static final String JPG = "JPG";
    private static final Map types = new HashMap() {


        {
            put("JPG", ".jpg");
        }
    };

}
