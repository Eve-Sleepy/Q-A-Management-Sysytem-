// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Uploader.java

package com.example.faq.ueditor.upload;

import com.example.faq.ueditor.define.State;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

// Referenced classes of package com.example.faq.ueditor.upload:
//            Base64Uploader, BinaryUploader

public class Uploader
{

    public Uploader(HttpServletRequest request, Map conf)
    {
        this.request = null;
        this.conf = null;
        this.request = request;
        this.conf = conf;
    }

    public final State doExec() throws IOException {
        String filedName = (String)conf.get("fieldName");
        State state = null;
        if("true".equals(conf.get("isBase64"))) {
            state = Base64Uploader.save(request.getParameter(filedName), conf);
        } else {
            state = BinaryUploader.save(request, conf);
        }
        return state;
    }
    private HttpServletRequest request;
    private Map conf;
}
