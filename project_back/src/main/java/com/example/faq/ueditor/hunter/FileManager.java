// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FileManager.java

package com.example.faq.ueditor.hunter;

import com.example.faq.ueditor.PathFormat;
import com.example.faq.ueditor.define.*;

import java.io.File;
import java.util.*;

import org.apache.commons.io.FileUtils;

public class FileManager {

    public FileManager(Map conf) {
        dir = null;
        rootPath = null;
        allowFiles = null;
        count = 0;
        rootPath = (String) conf.get("rootPath");
        dir = (new StringBuilder(String.valueOf(rootPath))).append((String) conf.get("dir")).toString();
        allowFiles = getAllowFiles(conf.get("allowFiles"));
        count = ((Integer) conf.get("count")).intValue();
    }

    public State listFile(int index) {
        File dir = new File(this.dir);
        State state = null;
        if (!dir.exists())
            return new BaseState(false, 302);
        if (!dir.isDirectory())
            return new BaseState(false, 301);
        Collection list = FileUtils.listFiles(dir, allowFiles, true);
        if (index < 0 || index > list.size()) {
            state = new MultiState(true);
        } else {
            Object fileList[] = Arrays.copyOfRange(list.toArray(), index, index + count);
            state = getState(fileList);
        }
        state.putInfo("start", index);
        state.putInfo("total", list.size());
        return state;
    }

    private State getState(Object files[]) {
        MultiState state = new MultiState(true);
        BaseState fileState = null;
        File file = null;
        Object aobj[];
        int j = (aobj = files).length;
        for (int i = 0; i < j; i++) {
            Object obj = aobj[i];
            if (obj == null)
                break;
            file = (File) obj;
            fileState = new BaseState(true);
            fileState.putInfo("url", PathFormat.format(getPath(file)));
            state.addState(fileState);
        }

        return state;
    }

    private String getPath(File file) {
        String path = file.getAbsolutePath();
        return path.replace(rootPath, "/");
    }

    private String[] getAllowFiles(Object fileExt) {
        String exts[] = null;
        String ext = null;
        if (fileExt == null)
            return new String[0];
        exts = (String[]) fileExt;
        int i = 0;
        for (int len = exts.length; i < len; i++) {
            ext = exts[i];
            exts[i] = ext.replace(".", "");
        }

        return exts;
    }

    private String dir;
    private String rootPath;
    private String allowFiles[];
    private int count;
}
