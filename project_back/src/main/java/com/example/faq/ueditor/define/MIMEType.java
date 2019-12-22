// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MIMEType.java

package com.example.faq.ueditor.define;

import java.util.HashMap;
import java.util.Map;

public class MIMEType
{

    public MIMEType()
    {
    }

    public static String getSuffix(String mime)
    {
        return (String)types.get(mime);
    }

    public static final Map types = new HashMap() {

            
            {
                put("image/gif", ".gif");
                put("image/jpeg", ".jpg");
                put("image/jpg", ".jpg");
                put("image/png", ".png");
                put("image/bmp", ".bmp");
            }
    }
;

}
