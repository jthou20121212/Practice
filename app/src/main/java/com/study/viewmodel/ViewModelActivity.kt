package com.study.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.enableSavedStateHandles
import androidx.lifecycle.get
import androidx.savedstate.SavedStateRegistry
import com.jthou.pro.crazy.R
import com.study.livedata.TestViewModel

class ViewModelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_view_model)

        val viewModel = ViewModelProvider(this)[TestViewModel::class.java]

        val viewModel2 by viewModels<TestViewModel>()

        // ViewModelProvider(this).get<TestViewModel>()

        supportFragmentManager.beginTransaction().add(android.R.id.content, ViewModelFragment.newInstance()).commitAllowingStateLoss()

        val key = ViewModelActivity::class.java.name
        savedStateRegistry.registerSavedStateProvider(key) {
            TODO()
        }
        savedStateRegistry.unregisterSavedStateProvider(key)
        val bundle = savedStateRegistry.consumeRestoredStateForKey(key)
        val value = bundle?.getString("Key")
    }

    @Deprecated("Deprecated in Java")
    override fun onRetainCustomNonConfigurationInstance(): Any {
        TODO()
    }

}