package com.jthou.pro.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Printer;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jthou.pro.crazy.R;
import com.utils.AnyExtKt;

// 用于测试 Looper MessagePrinter
public class MessagePrinterActivity extends AppCompatActivity {

    private long mStartTime, mEndTime;
    private final long threshold = 50;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barrier_message);

        setMessagePrinter();

        final Handler handler = new Handler(Looper.myLooper());
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(60);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        AnyExtKt.log("点击按钮发送消息");
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Looper.myLooper().setMessageLogging(null);
    }

    private void setMessagePrinter() {
        final String dispatchingPrefix = ">>>>> Dispatching to ";
        final String finishedPrefix = "<<<<< Finished to ";
        final Looper looper = Looper.myLooper();
        final Handler handler = new Handler(looper);
        looper.setMessageLogging(new Printer() {
            @Override
            public void println(String x) {
                AnyExtKt.log("println : " + x);
                if (x.startsWith(dispatchingPrefix)) {
                    mStartTime = System.currentTimeMillis();
                } else if (x.startsWith(finishedPrefix)) {
                    mEndTime = System.currentTimeMillis();
                    if (mEndTime - mStartTime > threshold) {
                        boolean postDelayed = handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                AnyExtKt.log("有个消息执行时间超出了 50 毫秒");
                            }
                        }, 50);
                        AnyExtKt.log("postDelayed : " + postDelayed);
                    }
                }
            }
        });
    }

}
