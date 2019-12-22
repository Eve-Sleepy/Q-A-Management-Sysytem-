// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Base64Uploader.java

package com.example.faq.ueditor.upload;

import com.example.faq.ueditor.PathFormat;
import com.example.faq.ueditor.define.*;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;

// Referenced classes of package com.example.faq.ueditor.upload:
//            StorageManager

public final class Base64Uploader
{

    public Base64Uploader()
    {
    }

    public static State save(String content, Map conf)
    {
        byte data[] = decode(content);
        long maxSize = ((Long)conf.get("maxSize")).longValue();
        if(!validSize(data, maxSize))
            return new BaseState(false, 1);
        String suffix = FileType.getSuffix("JPG");
        String savePath = PathFormat.parse((String)conf.get("savePath"), (String)conf.get("filename"));
        savePath = (new StringBuilder(String.valueOf(savePath))).append(suffix).toString();
        String physicalPath = (new StringBuilder(String.valueOf((String)conf.get("rootPath")))).append(savePath).toString();
        State storageState = StorageManager.saveBinaryFile(data, physicalPath);
        if(storageState.isSuccess())
        {
            storageState.putInfo("url", PathFormat.format(savePath));
            storageState.putInfo("type", suffix);
            storageState.putInfo("original", "");
        }
        return storageState;
    }

    private static byte[] decode(String content)
    {
        return Base64.decodeBase64(content);
    }

    private static boolean validSize(byte data[], long length)
    {
        return (long)data.length <= length;
    }
}
