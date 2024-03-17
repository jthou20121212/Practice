package com.jthou.pro.crazy;

import java.util.Objects;

/**
 * @author jthou
 * @date 30-07-2020
 * @since 1.0.0
 */
public abstract class BaseTask<T> implements Task<T>, Comparable<BaseTask<T>> {

    private boolean isCompleted;

    private T mResult;

    private final int mPriority;

    public BaseTask(int priority) {
        this.mPriority = priority;
    }

    private Behavior<T> mBehavior;

    public void setBehavior(Behavior<T> behavior) {
        this.mBehavior = behavior;
    }

    public Behavior<?> getBehavior() {
        return this.mBehavior;
    }

    public void setResult(T result) {
        this.mResult = result;
    }

    public T getResult() {
        return mResult;
    }

    public void setCompleted() {
        this.isCompleted = true;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public abstract void execute();

    @Override
    public int compareTo(BaseTask<T> o) {
        return this.mPriority - o.mPriority;
    }

    @Override
    public void showDialog(T t) {
        if (mBehavior == null) {
            return;
        }
        mBehavior.show(t);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseTask<?> baseTask = (BaseTask<?>) o;
        return mPriority == baseTask.mPriority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mPriority);
    }

}
