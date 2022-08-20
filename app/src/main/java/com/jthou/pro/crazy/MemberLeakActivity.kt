package com.jthou.pro.crazy

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.jthou.fuckyou.MemberLeakManager
import com.jthou.pro.crazy.databinding.ActivityMemberLeakBinding

class MemberLeakActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMemberLeakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val decodeResource = BitmapFactory.decodeResource(resources, R.drawable.fuck_my_life)
        binding.imageView.setImageBitmap(decodeResource)

        MemberLeakManager.addActivity(this)

        binding.imageView.setOnClickListener(object : View.OnClickListener, View.OnFocusChangeListener {
            override fun onClick(v: View?) {

            }

            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        MemberLeakManager.removeActivity(this)
    }

}