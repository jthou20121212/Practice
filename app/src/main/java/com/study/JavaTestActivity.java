package com.study;

import android.os.Bundle;
import android.util.Log;
import android.view.Choreographer;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.jthou.pro.crazy.Tab;
import com.jthou.pro.crazy.R;
import com.study.framerate.FpsMonitor;
import com.study.gson.GsonTypeToken;
import com.utils.AnyExtKt;

import java.util.List;

public class JavaTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_test);

        FpsMonitor.INSTANCE.startMonitor(new Function1<Integer, Unit>() {
            @Override
            public Unit invoke(Integer frameRate) {
                Log.i(AnyExtKt.TAG, "frameRate : " + frameRate);
                return Unit.INSTANCE;
            };
        });
    }

    @Override
    protected void onDestroy() {
        FpsMonitor.INSTANCE.stopMonitor();
        super.onDestroy();
    }

}