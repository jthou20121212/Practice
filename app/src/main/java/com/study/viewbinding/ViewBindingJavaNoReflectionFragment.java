package com.study.viewbinding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jthou.pro.crazy.databinding.FragmentViewBindingBinding;

/**
 * @author jthou
 * @date 08-03-2022
 * @since 1.0.0
 */
public class ViewBindingJavaNoReflectionFragment extends BaseJavaNoReflectionFragment<FragmentViewBindingBinding> {

    public static ViewBindingJavaNoReflectionFragment newInstance() {
        Bundle args = new Bundle();
        ViewBindingJavaNoReflectionFragment fragment = new ViewBindingJavaNoReflectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public FragmentViewBindingBinding createViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentViewBindingBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBinding().textView.setText(getClass().getSuperclass().getCanonicalName());
    }

}
