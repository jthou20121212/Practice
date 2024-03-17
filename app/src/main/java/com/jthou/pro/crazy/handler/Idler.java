package com.jthou.pro.crazy.handler;

import android.os.MessageQueue;

public class Idler implements MessageQueue.IdleHandler {

    private boolean mIdle;

    public void waitForIdle() {
        synchronized (this) {
            while (!mIdle) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean queueIdle() {
        synchronized (this) {
            mIdle = true;
            notifyAll();
        }
        return false;
    }

}
