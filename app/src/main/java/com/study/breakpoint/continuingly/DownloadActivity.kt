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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ActivityUtils
import com.jthou.fetch.*
import com.jthou.pro.crazy.R
import kotlinx.coroutines.launch
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLPeerUnverifiedException

class DownloadActivity : AppCompatActivity() {

    private val fetch by lazy { Fetch.Builder(this).build() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)

        Fetch.setup(this)

        val downloadList = mutableListOf(
            "https://cos.appmeta.cn/c97ea0b7485c383bde799d7fd996e1125d99bc56.apk?sign=6a2934b218a00e0bc3b8fe1e8ac1f25e&t=1662020702",
            "https://dl.clipber.com/mac/copies_2_1_11.dmg",
            "https://vfx.mtime.cn/Video/2019/03/12/mp4/190312143927981075.mp4",
            "https://1.eu.dl.wireshark.org/osx/Wireshark%20Latest%20Intel%2064.dmg",
            "https://r2---sn-5hne6nsd.gvt1.com/edgedl/android/studio/install/2021.2.1.16/android-studio-2021.2.1.16-mac.dmg?cms_redirect=yes&mh=SI&mip=188.166.96.124&mm=28&mn=sn-5hne6nsd&ms=nvh&mt=1662384028&mv=m&mvi=2&pl=19&rmhost=r1---sn-5hne6nsd.gvt1.com&shardbypass=sd&smhost=r3---sn-5hne6nzd.gvt1.com",
            "https://dl.clipber.com/release/android/41.apk",
            "https://pkg-cdn-hz0.sase.eagleyun.com/app/mac/2.4.11.42/yunshu_2.4.11.42.pkg?response-content-disposition=attachment%3Bfilename%3DYunShu_2.4.11.42_NIO.pkg",
        )
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = DownloadAdapter(fetch, downloadList)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    class DownloadAdapter(private val fetch: Fetch, private val downloadList: MutableList<String>) : RecyclerView.Adapter<DownloadViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadViewHolder {
            return DownloadViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_download, parent, false))
        }

        override fun onBindViewHolder(holder: DownloadViewHolder, position: Int) {
            val url = downloadList[position]
            val fileName = fetch.getFileName(url)
            holder.tvFileName.tag = fileName
            holder.tvFileName.text = fileName

            val progress = fetch.getProgress(url)
            holder.progressBar.progress = progress
            holder.btnDownload.text = when (progress) {
                0 -> "下载"
                100 -> "下载完成"
                else -> "继续下载"
            }
            holder.tvFileName.append("（$progress%）")

            holder.btnDownload.setOnClickListener {
                // 不能直接设置文案，可能正在下载或者已经下载完成
                holder.btnDownload.text = "正在检测是否支持多线程下载"
                val activity = ActivityUtils.getActivityByContext(holder.btnDownload.context) as? AppCompatActivity
                activity?.lifecycleScope?.launch {
                    fetch.download(url).collect { status ->
                        when (status) {
                            is DownloadStatus.Idle -> {

                            }

                            is DownloadStatus.Progress -> {
                                holder.progressBar.progress = status.percent
                                holder.tvFileName.text = "${holder.tvFileName.tag}（${status.percent}%）"
                                holder.btnDownload.text = when (status.percent) {
                                    100 -> "下载完成"
                                    else -> "正在下载"
                                }
                            }

                            is DownloadStatus.Started -> {
                                if (status.supportRange) {
                                    holder.btnDownload.text = "开始多线程下载"
                                } else {
                                    holder.btnDownload.text = "不支持范围请求不能使用断点续传"
                                }
                            }

                            is DownloadStatus.Completed -> {
                                holder.btnDownload.post {
                                    holder.btnDownload.text = "下载完成"
                                }
                            }

                            is DownloadStatus.Error -> {
                                when (status.throwable) {
                                    is UnknownHostException -> {
                                        holder.btnDownload.post {
                                            holder.btnDownload.text = "请检查网络连接"
                                        }
                                    }

                                    is SocketTimeoutException -> {
                                        holder.btnDownload.post {
                                            holder.btnDownload.text = "读超时请重试"
                                        }
                                    }

                                    is SocketException -> {
                                        holder.btnDownload.post {
                                            holder.btnDownload.text = "已取消，请点击重试"
                                        }
                                    }

                                    is SSLPeerUnverifiedException -> {
                                        holder.btnDownload.post {
                                            holder.btnDownload.text = "SSL证书验证失败"
                                        }
                                    }

                                    else -> {
                                        holder.btnDownload.post {
                                            holder.btnDownload.text = "未知错误"
                                        }

                                        Log.getStackTraceString(status.throwable).log()
                                    }
                                }
                            }
                        }
                    }
                }
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