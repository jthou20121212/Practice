package com.jthou.pro.handler;

import android.os.Message;

import java.util.HashMap;
import java.util.Map;

public class MessageContainer {

    private static volatile MessageContainer instance;

    public static MessageContainer getInstance() {
        if (instance == null) {
            synchronized (MessageContainer.class) {
                if (instance == null) {
                    instance = new MessageContainer();
                }
            }
        }
        return instance;
    }


    private final Map<Message, String> mMap = new HashMap<>();

    public void save(Message key, String value) {
        mMap.put(key, value);
    }

    public String get(Message key) {
        return mMap.get(key);
    }

    public void remove(Message key) {
        mMap.remove(key);
    }

}
