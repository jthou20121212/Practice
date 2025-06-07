package com.study.memory;

import android.os.Bundle;
import android.os.Process;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;

import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MemoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void test() {
        String scoreAdjPath = String.format(Locale.CHINA, "/proc/%d/oom_score_adj", Process.myPid());
        String adjPath = String.format(Locale.CHINA, "/proc/%d/oom_adj", Process.myPid());
        String s = FileIOUtils.readFile2String(scoreAdjPath);
    }

}
