// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   State.java

package com.example.faq.ueditor.define;


public interface State
{

    public abstract boolean isSuccess();

    public abstract void putInfo(String s, String s1);

    public abstract void putInfo(String s, long l);

    public abstract String toJSONString();
}
