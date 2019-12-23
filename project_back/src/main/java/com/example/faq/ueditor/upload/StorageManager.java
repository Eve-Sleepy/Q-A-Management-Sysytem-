// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   StorageManager.java

package com.example.faq.ueditor.upload;

import com.example.faq.ueditor.define.BaseState;
import com.example.faq.ueditor.define.State;

import java.io.*;

import org.apache.commons.io.FileUtils;

public class StorageManager {

    public StorageManager() {
    }

    public static State saveBinaryFile(byte data[], String path) {
        File file = new File(path);
        State state = valid(file);
        if (!state.isSuccess())
            return state;
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(data);
            bos.flush();
            bos.close();
        } catch (IOException ioe) {
            return new BaseState(false, 4);
        }
        state = new BaseState(true, file.getAbsolutePath());
        state.putInfo("size", data.length);
        state.putInfo("title", file.getName());
        return state;
    }

    public static State saveFileByInputStream(InputStream is, String path, long maxSize) {
        State state = null;
        File tmpFile = getTmpFile();
        byte dataBuf[] = new byte[2048];
        BufferedInputStream bis = new BufferedInputStream(is, 8192);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tmpFile), 8192);
            for (int count = 0; (count = bis.read(dataBuf)) != -1; ) {
                bos.write(dataBuf, 0, count);
            }
            bos.flush();
            bos.close();
            if (tmpFile.length() > maxSize) {
                tmpFile.delete();
                return new BaseState(false, 1);
            }
        } catch (IOException ioexception) {
            return new BaseState(false, 4);
        }
        state = saveTmpFile(tmpFile, path);
        if (!state.isSuccess()) {
            tmpFile.delete();
        }
        return state;
    }

    public static State saveFileByInputStream(InputStream is, String s, String path) {
        State state = null;
        File tmpFile = getTmpFile();
        byte dataBuf[] = new byte[2048];
        BufferedInputStream bis = new BufferedInputStream(is, 8192);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tmpFile), 8192);
            for (int count = 0; (count = bis.read(dataBuf)) != -1; )
                bos.write(dataBuf, 0, count);

            bos.flush();
            bos.close();
            state = saveTmpFile(tmpFile, path);
            if (!state.isSuccess())
                tmpFile.delete();
            return state;
        } catch (IOException ioexception) {
            return new BaseState(false, 4);
        }
    }

    private static File getTmpFile() {
        File tmpDir = FileUtils.getTempDirectory();
        String tmpFileName = (new StringBuilder(String.valueOf(Math.random() * 10000D))).toString().replace(".", "");
        return new File(tmpDir, tmpFileName);
    }

    private static State saveTmpFile(File tmpFile, String path) {
        State state = null;
        File targetFile = new File(path);
        if (targetFile.canWrite()) {
            return new BaseState(false, 2);
        }
        try {
            FileUtils.moveFile(tmpFile, targetFile);
        } catch (IOException e) {
            return new BaseState(false, 4);
        }
        state = new BaseState(true);
        state.putInfo("size", targetFile.length());
        state.putInfo("title", targetFile.getName());
        return state;
    }

    private static State valid(File file) {
        File parentPath = file.getParentFile();
        if (!parentPath.exists() && !parentPath.mkdirs())
            return new BaseState(false, 3);
        if (!parentPath.canWrite())
            return new BaseState(false, 2);
        else
            return new BaseState(true);
    }

    public static final int BUFFER_SIZE = 8192;
}
