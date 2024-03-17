package com.study.coordinator;

import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class MyBehavior extends CoordinatorLayout.Behavior <View>{

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof DependencyView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        int dependBottom = dependency.getBottom();

        child.setY(dependBottom + 50);
        child.setX(dependency.getLeft());

        return true;
    }

}