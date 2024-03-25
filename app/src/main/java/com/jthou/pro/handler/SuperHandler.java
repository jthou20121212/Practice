package com.jthou.pro.handler;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.utils.AnyExtKt;

// 这种方式可以知道这个消息是哪里发的
public class SuperHandler extends Handler {

    @Override
    public boolean sendMessageAtTime(@NonNull Message msg, long uptimeMillis) {
        boolean result = super.sendMessageAtTime(msg, uptimeMillis);
        if (result) {
            MessageContainer.getInstance().save(msg, Log.getStackTraceString(new Throwable()));
        }
        return result;
    }

    @Override
    public void dispatchMessage(@NonNull Message msg) {
        long startTime = System.currentTimeMillis();
        super.dispatchMessage(msg);
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;
        AnyExtKt.log("costTime : " + costTime);
        String stackTrace = MessageContainer.getInstance().get(msg);
        if (null == stackTrace) return;
        AnyExtKt.log("stackTrace : " + stackTrace);
    }

}
