package com.jthou.pro.crazy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jthou.pro.crazy.databinding.FragmentTestBinding

class TestFragment : Fragment() {

    companion object {
        private const val ARG_TAG = "arg_tag"
        private const val ARG_TITLE = "arg_title"
        fun newInstance(title: String, tag: String? = null): TestFragment {
            val fragment = TestFragment()
            val bundle = Bundle()
            bundle.putString(ARG_TAG, tag)
            bundle.putString(ARG_TITLE, title)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test, container, false).apply {
            val tag = arguments?.getString(ARG_TAG)
            this.tag = tag
            if (tag == MyOptionalActivity.TAG1) {
                this.setBackgroundResource(R.drawable.shape_radius)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = mutableListOf<String>()
        for (i in 1..100) {
            data.add(i.toString())
        }
        val binding = FragmentTestBinding.bind(view)
//        binding.recyclerView.apply {
//            adapter = TestAdapter(arguments?.getString(ARG_TITLE)!!, data)
//            layoutManager = LinearLayoutManager(context)
//            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
//        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    class TestViewHolder(private val view: View) : RecyclerView.ViewHolder(view)


}