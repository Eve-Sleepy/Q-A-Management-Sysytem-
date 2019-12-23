// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BaseState.java

package com.example.faq.ueditor.define;

import com.example.faq.ueditor.Encoder;

import java.util.*;

// Referenced classes of package com.example.faq.ueditor.define:
//            State, AppInfo

public class BaseState
        implements State {

    public BaseState() {
        state = false;
        info = null;
        infoMap = new HashMap();
        state = true;
    }

    public BaseState(boolean state) {
        this.state = false;
        info = null;
        infoMap = new HashMap();
        setState(state);
    }

    public BaseState(boolean state, String info) {
        this.state = false;
        this.info = null;
        infoMap = new HashMap();
        setState(state);
        this.info = info;
    }

    public BaseState(boolean state, int infoCode) {
        this.state = false;
        info = null;
        infoMap = new HashMap();
        setState(state);
        info = AppInfo.getStateInfo(infoCode);
    }

    public boolean isSuccess() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setInfo(int infoCode) {
        info = AppInfo.getStateInfo(infoCode);
    }

    public String toJSONString() {
        return toString();
    }

    public String toString() {
        String key = null;
        String stateVal = isSuccess() ? AppInfo.getStateInfo(0) : info;
        StringBuilder builder = new StringBuilder();
        builder.append((new StringBuilder("{\"state\": \"")).append(stateVal).append("\"").toString());
        for (Iterator iterator = infoMap.keySet().iterator(); iterator.hasNext(); builder.append((new StringBuilder(",\"")).append(key).append("\": \"").append((String) infoMap.get(key)).append("\"").toString()))
            key = (String) iterator.next();

        builder.append("}");
        return Encoder.toUnicode(builder.toString());
    }

    public void putInfo(String name, String val) {
        infoMap.put(name, val);
    }

    public void putInfo(String name, long val) {
        putInfo(name, (new StringBuilder(String.valueOf(val))).toString());
    }

    private boolean state;
    private String info;
    private Map infoMap;
}
