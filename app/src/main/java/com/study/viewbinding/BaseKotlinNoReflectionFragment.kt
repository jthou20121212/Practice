package com.study.viewbinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Kotlin Fragment 不使用反射需要子类传入一个函数
 *
 * @author jthou
 * @since 1.0.0
 * @date 08-03-2022
 */
open class BaseKotlinNoReflectionFragment<VB: ViewBinding>(val viewBinder: (LayoutInflater, ViewGroup?, Boolean) -> VB): Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return viewBinder(inflater, container, false).also { _binding = it }.root
    }

}