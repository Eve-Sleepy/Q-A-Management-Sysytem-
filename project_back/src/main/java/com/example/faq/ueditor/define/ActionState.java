// // Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// // Jad home page: http://www.kpdus.com/jad.html
// // Decompiler options: packimports(3)
// // Source File Name:   ActionState.java
//
// package com.example.faq.ueditor.define;
//
//
// public final class ActionState extends AbstractEnum
// {
//
//     private ActionState(String s, int i)
//     {
//         super(s, i);
//     }
//
//     public static ActionState[] values()
//     {
//         ActionState aactionstate[];
//         int i;
//         ActionState aactionstate1[];
//         System.arraycopy(aactionstate = ENUM$VALUES, 0, aactionstate1 = new ActionState[i = aactionstate.length], 0, i);
//         return aactionstate1;
//     }
//
//     public static ActionState valueOf(String s)
//     {
//         return (ActionState)AbstractEnum.valueOf("com/example/faq/define/ActionState", s);
//     }
//
//     public static final ActionState UNKNOW_ERROR;
//     private static final ActionState ENUM$VALUES[];
//
//     static
//     {
//         UNKNOW_ERROR = new ActionState("UNKNOW_ERROR", 0);
//         ENUM$VALUES = (new ActionState[] {
//             UNKNOW_ERROR
//         });
//     }
// }

package com.example.faq.ueditor.define;

public enum ActionState {
    UNKNOW_ERROR
}

