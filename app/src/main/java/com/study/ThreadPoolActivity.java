package com.study;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jthou.pro.crazy.R;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ThreadPoolActivity extends AppCompatActivity {

    private final Object lock = new Object();

    private Object object;

    {
        object = new Object();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        synchronized (lock) {
            // do something
        }
        try {
            int hashCode = object.hashCode();
            if (hashCode != 0) {
                // do something
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);
        Runnable task = new Runnable() {
            @Override
            public void run() {

            }
        };
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return null;
            }
        };
        ThreadPoolExecutor cachedThreadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        cachedThreadPool.execute(task);
        Future<String> future = cachedThreadPool.submit(callable);
        Future<?> submit = cachedThreadPool.submit(task);
        long taskCount = cachedThreadPool.getTaskCount();
        long completedTaskCount = cachedThreadPool.getCompletedTaskCount();
        try {
            Object o = submit.get();
            try {
                String s = future.get(1, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}