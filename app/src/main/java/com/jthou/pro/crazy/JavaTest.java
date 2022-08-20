package com.jthou.pro.crazy;


import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.util.Printer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ObjectUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author jthou
 * @date 16-03-2021
 * @since 1.0.0
 */
public class JavaTest {

    private static Handler mHandler;

    public static void main(String[] args) {

        Looper.getMainLooper().setMessageLogging(new Printer() {
            @Override
            public void println(String x) {

            }
        });


//        HashMap<String, Object> map = new HashMap<>();
//        Set<Map.Entry<String, Object>> entries = map.entrySet();
//        for (Map.Entry<String, Object> next : entries) {
//            String key = next.getKey();
//            Object value = next.getValue();
//        }
//
//        Hashtable<String, Object> hashtable = new Hashtable<>();
//        Set<Map.Entry<String, Object>> entries1 = hashtable.entrySet();

//        Glide.with(this).load("").fallback(R.drawable.fuck_my_life).into(new SimpleTarget<Drawable>() {
//            @Override
//            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//
//            }
//        })

//        OkHttpClient httpClient = new OkHttpClient();
//        Request request = new Request.Builder().build();
//        try {
//            // 同步
//            Response execute = httpClient.newCall(request).execute();
//            // 异步
//            httpClient.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(@NotNull Call call, @NotNull IOException e) {
//
//                }
//
//                @Override
//                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        mHandler = new Handler(Looper.myLooper()) {
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                super.handleMessage(msg);
//            }
//        };
//
//        for (;;) {
//
//        }

//        JavaTest test = new JavaTest();
//        test.lruTest();

        JavaTest javaTest = new JavaTest();
        javaTest.lruTest();

        SimpleDateFormat format = new SimpleDateFormat();


    }

    private static Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mHandler.postDelayed(this, 1000);
        }
    };

    private void lruTest() {
        LruCache<Integer, Integer> lruCache = new LruCache<>(5);
        lruCache.put(1, 1);
        lruCache.put(2, 2);
        lruCache.put(3, 3);
        lruCache.put(4, 4);
        lruCache.put(5, 5);
        for (Map.Entry<Integer, Integer> entry : lruCache.snapshot().entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        System.out.println("超出设定存储容量后");
        lruCache.put(6, 6);
        for (Map.Entry<Integer, Integer> entry : lruCache.snapshot().entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

}
