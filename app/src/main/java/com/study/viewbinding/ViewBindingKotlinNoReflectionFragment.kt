package com.study.viewbinding

import android.os.Bundle
import android.view.View
import com.jthou.pro.crazy.databinding.FragmentViewBindingBinding

class ViewBindingKotlinNoReflectionFragment : BaseKotlinNoReflectionFragment<FragmentViewBindingBinding>(FragmentViewBindingBinding::inflate) {

    companion object {
        fun newInstance() : ViewBindingKotlinNoReflectionFragment = ViewBindingKotlinNoReflectionFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView.text = javaClass.superclass.canonicalName
    }

}