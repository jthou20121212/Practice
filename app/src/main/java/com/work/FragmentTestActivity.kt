package com.work

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.FragmentUtils
import com.jthou.pro.crazy.PreviewPdfFileActivity
import com.jthou.pro.crazy.R
import com.utils.log
import splitties.activities.start

class FragmentTestActivity : AppCompatActivity(R.layout.activity_test), Test1Fragment.OnInteractionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FragmentUtils.replace(supportFragmentManager, Test2Fragment(), R.id.container_2)
        FragmentUtils.replace(supportFragmentManager, Test1Fragment(), R.id.container_1)
    }

    override fun showComments() {
//        // FragmentUtils.replace(supportFragmentManager, Test2Fragment(), R.id.container_2)
//        supportFragmentManager.fragments.forEach {
//            if (it is Test2Fragment) {
////                FragmentUtils.remove(it)
//                Handler(Looper.getMainLooper()).post {
//                    FragmentUtils.remove(it)
//                }
//            }
//        }
    }
}


class Test1Fragment : Fragment(R.layout.fragment_test1) {
    var listener: OnInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnInteractionListener) {
            listener = context
        }
    }

    override fun onResume() {
        super.onResume()
        listener?.showComments()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 先 remove
        val fragmentActivity = activity as? FragmentTestActivity ?: return
        fragmentActivity.supportFragmentManager.fragments.forEach {
            if (it is Test2Fragment) {
                "remove Test2Fragment 1".log()
                if (!it.isAdded || it.isRemoving || it.isDetached) return
                "remove Test2Fragment 2".log()
                FragmentUtils.remove(it)
//                    Handler(Looper.getMainLooper()).post {
//                        FragmentUtils.remove(it)
//                    }
            }
        }
        // 再跳转到其他页面
        fragmentActivity.start<PreviewPdfFileActivity>()
        // 最后关闭自己
        fragmentActivity.finish()
    }

    interface OnInteractionListener {
        fun showComments()
    }
}

class Test2Fragment : Fragment(R.layout.fragment_test2)