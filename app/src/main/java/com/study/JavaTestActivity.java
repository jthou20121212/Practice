package com.study;

import android.os.Bundle;
import android.view.Choreographer;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.jthou.fuckyou.Tab;
import com.jthou.pro.crazy.R;
import com.study.gson.GsonTypeToken;

import java.util.List;

public class JavaTestActivity extends AppCompatActivity implements Choreographer.FrameCallback {

    private long mFrameTimeNanos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_test);

        // Observable.

        Choreographer.getInstance().postFrameCallback(this);

        Tab tab = new Gson().fromJson("{}", Tab.class);
        List<Tab> tabList = new Gson().fromJson("[]", new GsonTypeToken<List<Tab>>().getType());

        boolean compare = int.class == Integer.TYPE;


        // MMKV.defaultMMKV().putBoolean().putInt().apply();

//        ContextKt.start(this, new Function1<Intent, Unit>() {
//            @Override
//            public Unit invoke(Intent intent) {
//                return null;
//            }
//        });

        //获取手机屏幕刷新频率
        Display display = getWindowManager().getDefaultDisplay();
        float refreshRate = display.getRefreshRate();
        LogUtils.i("jthou", "refreshRate : " + refreshRate);
    }

    @Override
    public void doFrame(long frameTimeNanos) {
        Choreographer.getInstance().postFrameCallback(this);
        if (mFrameTimeNanos > 0) {
            long value = frameTimeNanos - mFrameTimeNanos;
            LogUtils.i("jthou", "value : " + value);
        }
        mFrameTimeNanos = frameTimeNanos;
    }

    @Override
    protected void onDestroy() {
        Choreographer.getInstance().removeFrameCallback(this);
        super.onDestroy();
    }

}