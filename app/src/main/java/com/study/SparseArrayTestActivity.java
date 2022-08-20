package com.study;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.jthou.pro.crazy.databinding.ActivitySparseArrayTestBinding;


public class SparseArrayTestActivity extends AppCompatActivity {

    private static final String TAG = "SparseArrayTestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySparseArrayTestBinding binding = ActivitySparseArrayTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SparseArray<String> sparseArray = new SparseArray<>();
        sparseArray.put(1, "1");
        sparseArray.put(2, "2");
        sparseArray.put(3, "3");
        binding.tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sparseArray.put(3, "3.3");

                Log.i(TAG, "sparseArray : " + sparseArray);
                Log.i(TAG, "sparseArray.size : " + sparseArray.size());
            }
        });
        binding.tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sparseArray.remove(1);

                Log.i(TAG, "sparseArray : " + sparseArray);
                Log.i(TAG, "sparseArray.size : " + sparseArray.size());
            }
        });
    }
}