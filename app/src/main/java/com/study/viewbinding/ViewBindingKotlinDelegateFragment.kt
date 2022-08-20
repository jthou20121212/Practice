package com.study.viewbinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jthou.pro.crazy.R
import com.jthou.pro.crazy.databinding.FragmentViewBindingBinding

class ViewBindingKotlinDelegateFragment : Fragment() {

    private val binding by viewBinding(FragmentViewBindingBinding::bind)

    companion object {
        fun newInstance() : ViewBindingKotlinDelegateFragment = ViewBindingKotlinDelegateFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view_binding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView.text = javaClass.superclass.canonicalName
    }

}