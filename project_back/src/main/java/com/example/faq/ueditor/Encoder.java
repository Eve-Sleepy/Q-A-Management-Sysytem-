// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Encoder.java

package com.example.faq.ueditor;


public class Encoder
{

    public Encoder()
    {
    }

    public static String toUnicode(String input)
    {
        StringBuilder builder = new StringBuilder();
        char chars[] = input.toCharArray();
        char ac[];
        int j = (ac = chars).length;
        for(int i = 0; i < j; i++)
        {
            char ch = ac[i];
            if(ch < '\u0100')
                builder.append(ch);
            else
                builder.append((new StringBuilder("\\u")).append(Integer.toHexString(ch & 0xffff)).toString());
        }

        return builder.toString();
    }
}
