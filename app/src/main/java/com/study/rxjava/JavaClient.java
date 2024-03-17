package com.study.rxjava;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

class JavaClient {

    public static void main(String[] args) {
        Observable.interval(1, TimeUnit.MINUTES)
                .takeUntil(aLong -> aLong == 4)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (aLong == 4) {
                            System.out.println(aLong);
                        }
                    }
                });
    }

}
