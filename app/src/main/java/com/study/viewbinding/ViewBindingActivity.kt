package com.study.viewbinding

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.blankj.utilcode.util.LogUtils
import com.jthou.pro.crazy.R
import com.jthou.pro.crazy.databinding.ActivityViewBindingBinding

/**
 *
 *
 * @author jthou
 * @since 1.0.0
 * @date 08-03-2022
 */
class ViewBindingActivity : AppCompatActivity() {

//    private val binding by viewBinding(ActivityViewBindingBinding::inflate)
    private val binding2 by viewBinding2(ActivityViewBindingBinding::inflate)
//    private val binding3 by viewBinding3(ActivityViewBindingBinding::bind, R.layout.activity_view_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 使用 com.study.viewbinding.ViewBindingExKt.viewBinding3
        // binding3.textView.text = binding3.toString()

        // 使用 com.study.viewbinding.ViewBindingExKt.viewBinding2
         binding2.textView.text = binding2.toString()

        // 使用 com.study.viewbinding.ViewBindingExKt.viewBinding(androidx.activity.ComponentActivity, kotlin.jvm.functions.Function1<? super android.view.LayoutInflater,? extends VB>)
        // binding.textView.text = binding.toString()


//        supportFragmentManager
//            .beginTransaction()
//            .replace(android.R.id.content, ViewBindingKotlinDelegateFragment.newInstance())
////            .replace(android.R.id.content, ViewBindingKotlinNoReflectionFragment.newInstance())
////            .replace(android.R.id.content, ViewBindingKotlinFragment.newInstance())
////            .replace(android.R.id.content, ViewBindingJavaNoReflectionFragment.newInstance())
////            .replace(android.R.id.content, ViewBindingJavaWithReflectionFragment.newInstance())
//            .commitAllowingStateLoss()


        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                TODO("Not yet implemented")
            }

            override fun onActivityStarted(activity: Activity) {
                TODO("Not yet implemented")
            }

            override fun onActivityResumed(activity: Activity) {
                TODO("Not yet implemented")
            }

            override fun onActivityPaused(activity: Activity) {
                TODO("Not yet implemented")
            }

            override fun onActivityStopped(activity: Activity) {
                TODO("Not yet implemented")
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                TODO("Not yet implemented")
            }

            override fun onActivityDestroyed(activity: Activity) {
                TODO("Not yet implemented")
            }

        })

    }

}