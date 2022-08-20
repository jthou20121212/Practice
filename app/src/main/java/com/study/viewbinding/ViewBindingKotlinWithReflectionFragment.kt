package com.study.viewbinding

import android.os.Bundle
import android.view.View
import com.jthou.pro.crazy.databinding.FragmentViewBindingBinding

class ViewBindingKotlinWithReflectionFragment : BaseKotlinWithReflectionFragment<FragmentViewBindingBinding>() {

    companion object {
        fun newInstance() : ViewBindingKotlinWithReflectionFragment = ViewBindingKotlinWithReflectionFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView.text = javaClass.superclass.canonicalName
    }

}