package com.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jthou.pro.crazy.R
import com.jthou.pro.crazy.databinding.FragmentIndustryDataBinding
import com.study.viewbinding.viewBinding

class IndustryDataFragment : Fragment() {

    private val binding by viewBinding(FragmentIndustryDataBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_industry_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView.text = "行业数据"
    }

    companion object {
        @JvmStatic
        fun newInstance() = IndustryDataFragment()
    }
}