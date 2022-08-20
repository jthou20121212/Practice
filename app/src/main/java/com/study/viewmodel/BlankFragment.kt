package com.study.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.jthou.pro.crazy.R

class BlankFragment : Fragment() {

    companion object {
        fun newInstance() = BlankFragment()
    }

    private val viewModel by activityViewModels<BlankViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.blank_fragment, container, false)
    }

}