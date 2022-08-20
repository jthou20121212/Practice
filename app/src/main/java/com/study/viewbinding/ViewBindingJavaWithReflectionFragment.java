package com.study.viewbinding;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jthou.pro.crazy.databinding.FragmentViewBindingBinding;

/**
 * @author jthou
 * @date 08-03-2022
 * @since 1.0.0
 */
public class ViewBindingJavaWithReflectionFragment extends BaseJavaWithReflectionFragment<FragmentViewBindingBinding> {

    public static ViewBindingJavaWithReflectionFragment newInstance() {
        Bundle args = new Bundle();
        ViewBindingJavaWithReflectionFragment fragment = new ViewBindingJavaWithReflectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBinding().textView.setText(getClass().getSuperclass().getCanonicalName());
    }

}
