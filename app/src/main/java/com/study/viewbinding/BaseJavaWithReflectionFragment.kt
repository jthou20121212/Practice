package com.study.viewbinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * Java Fragment 使用了反射子类不需要重写方法
 *
 * @author jthou
 * @since 1.0.0
 * @date 08-03-2022
 */
open class BaseJavaWithReflectionFragment<VB: ViewBinding> : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    @CallSuper
    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val clazz = type.actualTypeArguments[0] as Class<VB>
            val method = clazz.getMethod("inflate", LayoutInflater::class.java)
            _binding = method.invoke(null, layoutInflater) as VB
            return binding.root
        }
        return null
    }

}