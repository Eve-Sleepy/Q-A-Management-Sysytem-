// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MultiState.java

package com.example.faq.ueditor.define;

import com.example.faq.ueditor.Encoder;

import java.util.*;

// Referenced classes of package com.example.faq.ueditor.define:
//            State, AppInfo

public class MultiState
        implements State {

    public MultiState(boolean state) {
        this.state = false;
        info = null;
        intMap = new HashMap();
        infoMap = new HashMap();
        stateList = new ArrayList();
        this.state = state;
    }

    public MultiState(boolean state, String info) {
        this.state = false;
        this.info = null;
        intMap = new HashMap();
        infoMap = new HashMap();
        stateList = new ArrayList();
        this.state = state;
        this.info = info;
    }

    public MultiState(boolean state, int infoKey) {
        this.state = false;
        info = null;
        intMap = new HashMap();
        infoMap = new HashMap();
        stateList = new ArrayList();
        this.state = state;
        info = AppInfo.getStateInfo(infoKey);
    }

    public boolean isSuccess() {
        return state;
    }

    public void addState(State state) {
        stateList.add(state.toJSONString());
    }

    public void putInfo(String name, String val) {
        infoMap.put(name, val);
    }

    public String toJSONString() {
        String stateVal = isSuccess() ? AppInfo.getStateInfo(0) : info;
        StringBuilder builder = new StringBuilder();
        builder.append((new StringBuilder("{\"state\": \"")).append(stateVal).append("\"").toString());
        for (Iterator iterator = intMap.keySet().iterator(); iterator.hasNext(); builder.append((new StringBuilder(",\"")).append(stateVal).append("\": ").append(intMap.get(stateVal)).toString()))
            stateVal = (String) iterator.next();

        for (Iterator iterator = infoMap.keySet().iterator(); iterator.hasNext(); builder.append((new StringBuilder(",\"")).append(stateVal).append("\": \"").append((String) infoMap.get(stateVal)).append("\"").toString()))
            stateVal = (String) iterator.next();

        builder.append(", list: [");
        for (Iterator iterator = stateList.iterator(); iterator.hasNext(); builder.append((new StringBuilder(String.valueOf((String) iterator.next()))).append(",").toString()))
            ;
        if (stateList.size() > 0)
            builder.deleteCharAt(builder.length() - 1);
        builder.append(" ]}");
        return Encoder.toUnicode(builder.toString());
    }

    public void putInfo(String name, long val) {
        intMap.put(name, Long.valueOf(val));
    }

    private boolean state;
    private String info;
    private Map intMap;
    private Map infoMap;
    private List stateList;
}
