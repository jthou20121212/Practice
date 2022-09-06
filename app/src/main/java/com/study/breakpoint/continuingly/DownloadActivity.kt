package com.study.breakpoint.continuingly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jthou.fetch.*
import com.jthou.pro.crazy.R
import java.io.File
import java.net.SocketTimeoutException

class DownloadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)

        Fetch.setup(this)

        val downloadList = mutableListOf(
            "https://cos.appmeta.cn/c97ea0b7485c383bde799d7fd996e1125d99bc56.apk?sign=6a2934b218a00e0bc3b8fe1e8ac1f25e&t=1662020702",
            "https://img.huxiucdn.com/pdf/Facebook.pdf?abc=234",
            "https://vfx.mtime.cn/Video/2019/03/12/mp4/190312143927981075.mp4",
            "https://1.eu.dl.wireshark.org/osx/Wireshark%203.6.7%20Intel%2064.dmg",
            "https://r2---sn-5hne6nsd.gvt1.com/edgedl/android/studio/install/2021.2.1.16/android-studio-2021.2.1.16-mac.dmg?cms_redirect=yes&mh=SI&mip=188.166.96.124&mm=28&mn=sn-5hne6nsd&ms=nvh&mt=1662384028&mv=m&mvi=2&pl=19&rmhost=r1---sn-5hne6nsd.gvt1.com&shardbypass=sd&smhost=r3---sn-5hne6nzd.gvt1.com",
        )
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = DownloadAdapter(downloadList)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    class DownloadAdapter(private val downloadList: MutableList<String>) : RecyclerView.Adapter<DownloadViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadViewHolder {
            return DownloadViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_download, parent, false))
        }

        override fun onBindViewHolder(holder: DownloadViewHolder, position: Int) {
            val url = downloadList[position]
            val fetch = Fetch.Builder(holder.itemView.context).downloadListener(object : DownloadListener.SimpleDownloadListener() {
                override fun supportRangeRequest(supported: Boolean) {
                    holder.btnDownload.post {
                        if (supported) {
                            holder.btnDownload.text = "开始多线程下载"
                        } else {
                            holder.btnDownload.text = "不支持范围请求不能使用断点续传"
                        }
                    }
                }

                override fun downloadCompleted(file: File) {
                    Log.i("jthou", "downloadCompleted : ${file.absolutePath}")
                }

                override fun downloadFailure(e: Exception) {
                    // 如果抛出超时异常
                    if (e is SocketTimeoutException) {
                        holder.btnDownload.post {
                            holder.btnDownload.text = "读超时请重试"
                        }
                    }
                }

                override fun downloadProgress(progress: Int) {
                    Log.i("jthou", "progress : $progress")
                    holder.progressBar.progress = progress
                    holder.tvFileName.post {
                        holder.tvFileName.text = "${holder.tvFileName.tag}（$progress%）"
                        holder.btnDownload.text = when(progress) {
                            100 -> "下载完成"
                            else -> "正在下载"
                        }
                    }
                }
            }).build()
            val fileName = fetch.getFileName(url)
            holder.tvFileName.tag = fileName
            holder.tvFileName.text = fileName

            val progress = fetch.getProgress(url)
            holder.progressBar.progress = progress
            holder.btnDownload.text = when(progress) {
                0 -> "下载"
                100 -> "下载完成"
                else -> "继续下载"
            }
            holder.tvFileName.append("（$progress%）")

            holder.btnDownload.setOnClickListener {
                holder.btnDownload.text = "正在检测是否支持多线程下载"
                fetch.go(downloadList[position])
            }

            holder.btnPause.setOnClickListener {
                fetch.pause(downloadList[position])
                holder.btnDownload.text = "继续下载"
            }

            holder.btnDelete.setOnClickListener {
                fetch.delete(url)
                holder.progressBar.progress = 0
                holder.btnDownload.text = "下载"
                "${holder.tvFileName.tag}（0%）".also { holder.tvFileName.text = it }
            }
        }

        override fun getItemCount(): Int {
            return downloadList.size
        }

    }

    class DownloadViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvFileName: TextView = itemView.findViewById(R.id.tv_file_name)

        // 下载
        val btnDownload: Button = itemView.findViewById(R.id.button1)

        // 暂停
        val btnPause: Button = itemView.findViewById(R.id.button2)

        // 删除（不管是否下载完成）
        val btnDelete: Button = itemView.findViewById(R.id.button3)

        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)

    }

}