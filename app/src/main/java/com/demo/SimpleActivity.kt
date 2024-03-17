package com.demo

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.postDelayed
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gyf.immersionbar.ImmersionBar
import com.jthou.pro.crazy.R
import com.utils.log


class SimpleActivity : AppCompatActivity() {

    private val mainBodyAdapter by lazy {
        SimpleAdapter()
    }

    private val includeAdapter by lazy {
        SimpleAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple)

        val mainBodyList = mutableListOf("m1", "m2", "m1", "m2")
        mainBodyAdapter.setNewInstance(mainBodyList)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = mainBodyAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val include = LayoutInflater.from(this).inflate(R.layout.include_recycler_view, null)
        val root = include as RecyclerView
        val includeList = mutableListOf("i1", "i2", "i3", "i4", "i5", "i6", "i7", "i8")
        includeAdapter.setNewInstance(includeList)
        root.adapter = includeAdapter
        root.layoutManager = LinearLayoutManager(this)
        mainBodyAdapter.addHeaderView(root)
        recyclerView.postDelayed(1000) {
            "size : ${mainBodyAdapter.data.size}".log()
            "itemCount : ${mainBodyAdapter.itemCount}".log()
            "childCount : ${recyclerView.childCount}".log()
            "headerLayoutCount : ${mainBodyAdapter.headerLayoutCount}".log()
            "getStatusBarHeight : ${ImmersionBar.getStatusBarHeight(this)}".log()
        }
    }

}