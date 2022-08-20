package com.study.viewbinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Java Fragment 不使用反射需要子类重写一个方法
 *
 * @author jthou
 * @since 1.0.0
 * @date 08-03-2022
 */
abstract class BaseJavaNoReflectionFragment<VB: ViewBinding> : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createViewBinding(inflater, container).also { _binding = it }.root
    }

    abstract fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) : VB

}