package com.jthou.pro.handler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.jthou.pro.crazy.R;
import com.utils.AnyExtKt;

public class BlockTestActivity extends AppCompatActivity {

    private final Object mLock = new Object();
    private boolean mNotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_test);
        findViewById(R.id.btn_wait).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AnyExtKt.log("wait thread before");
                        synchronized (mLock) {
                            try {
                                mNotify = false;
                                while (!mNotify) {
                                    mLock.wait();
                                }
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        AnyExtKt.log("wait thread after");
                    }
                }).start();
            }
        });
        findViewById(R.id.btn_notify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                synchronized (mLock) {
                    mNotify = true;
                    mLock.notify();
                    AnyExtKt.log("notify");
                }
            }
        });
        findViewById(R.id.btn_print).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnyExtKt.log("走你");
            }
        });
    }


}