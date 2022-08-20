package com.jthou.pro.crazy

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jthou.fuckyou.TextViewAdapter
import com.jthou.pro.crazy.databinding.ActivityTextViewBinding

/**
 *
 *
 * @author jthou
 * @since 1.0.0
 * @date 24-11-2020
 */
class TextViewActivity : AppCompatActivity() {

    companion object {
        fun launchActivity(context: Context) {
            val intent = Intent(context, TextViewActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTextViewBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_text_view)
        binding.recyclerView.apply {
            adapter = TextViewAdapter()
            layoutManager = LinearLayoutManager(this@TextViewActivity)
            val divider =  DividerItemDecoration(this@TextViewActivity, DividerItemDecoration.VERTICAL)
            divider.setDrawable(ColorDrawable(Color.RED))
            addItemDecoration(divider)
        }
    }

}