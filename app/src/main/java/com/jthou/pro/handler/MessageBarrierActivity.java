package com.jthou.pro.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ThreadUtils;
import com.jthou.pro.crazy.R;
import com.utils.AnyExtKt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

// 用于测试 Android 同步屏障消息
public class MessageBarrierActivity extends AppCompatActivity {

    private Button mButton;
    // 网络请求返回的数据
    private String mNetWorkData;
    // 创建屏障消息会生成一个token，这个token用来删除屏障消息，很重要。
    private int mBarrierToken;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barrier_message);
        // 1. 发送一个同步屏障消息
        postSyncBarrier();
        // 2. 发送网络请求
        fetchData();
        // 3. 初始化组件
        initViews();
        // 4. 移除屏障消息
        removeSyncBarrier();
        // setSlowLogThreshold();
    }

    private void removeSyncBarrier() {
        // 4、待控件初始化完成，把异步线程设置的屏障消息remove掉，这样异步线程请求数据完成后，3、处的刷新UI界面的同步消息就有机会执行，就可以安全得刷新界面了。
        // thread.getLooper().getQueue().removeSyncBarrier(barrierToken);
        try {
            MessageQueue queue = mHandler.getLooper().getQueue();
            Method removeSyncBarrier = queue.getClass().getDeclaredMethod("removeSyncBarrier", int.class);
            removeSyncBarrier.setAccessible(true);
            removeSyncBarrier.invoke(queue, mBarrierToken);
            Log.i("---jthou---", "removeSyncBarrier 完成。当前是否是主线程：" + ThreadUtils.isMainThread());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void postSyncBarrier() {
        // 2、然后给异步线程的队列发一个屏障消息推入消息队列
        try {
            MessageQueue queue = mHandler.getLooper().getQueue();
            Method postSyncBarrier = queue.getClass().getDeclaredMethod("postSyncBarrier");
            postSyncBarrier.setAccessible(true);
            mBarrierToken = (int) postSyncBarrier.invoke(queue);
            Log.i("---jthou---", "postSyncBarrier 完成。当前是否是主线程：" + ThreadUtils.isMainThread());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 3. 模拟获取网络请求
                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // 异步耗时操作：网络请求数据，赋值给netWorkData
                mNetWorkData = "我是模拟网络请求的数据";
                Log.i("---jthou---", "获取接口数据完成。当前是否是主线程：" + ThreadUtils.isMainThread());
                // 4. 网络请求返回后将更新 UI 的任务放在主线程里执行
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mButton.setText(mNetWorkData);
                        Log.i("---jthou---", "组件赋值完成。当前是否是主线程：" + ThreadUtils.isMainThread());
                        Log.i("---jthou---", "");
                    }
                });
            }
        }).start();
    }

    private void initViews() {
        try {
            // 模拟初始化组件耗时
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        mButton = findViewById(R.id.button);
        Log.i("---jthou---", "控件初始化完成。当前是否是主线程：" + ThreadUtils.isMainThread());
    }

    private void setSlowLogThreshold() {
        try {
            // 获取setSlowLogThresholdMs方法
            Looper looper = Looper.myLooper();
            Method method = looper.getClass().getMethod("setSlowLogThresholdMs", long.class, long.class);
            // 调用方法
            long slowDispatchThresholdMs = 50; // 你要设置的值
            long slowDeliveryThresholdMs = 50; // 你要设置的值
            method.invoke(looper, slowDispatchThresholdMs, slowDeliveryThresholdMs);
            AnyExtKt.log("setSlowLogThreshold");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
