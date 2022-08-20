package com.study.viewmodel

import android.os.Bundle
import androidx.fragment.app.Fragment

class ViewModelFragment : Fragment() {

    companion object {
        fun newInstance(): ViewModelFragment {
            return ViewModelFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}