package com.example.faq.ueditor.define;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AbstractEnum {


    private static final Map<String, AbstractEnum> nameEnumMap = new ConcurrentHashMap<>();


    protected String name;


    protected AbstractEnum() {

    }


    protected AbstractEnum(String name) {

        this.name = name;

        if (!nameEnumMap.containsKey(name)) {

            nameEnumMap.put(name, this);

        }

    }

    public AbstractEnum(String s, int i) {
    }


    public String toString() {

        return this.name;

    }


    public static AbstractEnum valueOf(String s, String name) {

        if (name == null)

            throw new NullPointerException("Name is null");


        AbstractEnum result = nameEnumMap.get(name);

        if (result != null) {

            return result;

        }


        throw new IllegalArgumentException(

                "No enum constant exists, name is." + name);

    }


    public static void init() {

    }


}
