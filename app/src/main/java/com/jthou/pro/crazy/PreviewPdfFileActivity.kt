package com.jthou.pro.crazy

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.util.Constants
import com.jthou.pro.crazy.databinding.ActivityPreviewPdfFileBinding
import splitties.views.onClick
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

/**
 * 预览 PDF 文件
 *
 * @author jthou
 * @since 1.0.0
 * @date 02-12-2020
 */
class PreviewPdfFileActivity : AppCompatActivity() {

    private val binding by lazy { ActivityPreviewPdfFileBinding.inflate(layoutInflater) }

    private var mSwitch = false

    private var mPage = 0

    companion object {
        const val PDF_FILE_NAME1 = "Rx 在 Zhihu 的历史 - 杨凡.pdf"
        const val PDF_FILE_NAME2 = "虎嗅科技OKR工作坊讲义-201906.pdf"
//        const val PDF_URL = "https://file.finance.qq.com/finance/hs/pdf/2022/04/23/1213055132.PDF"
//        const val PDF_URL = "https://file.finance.qq.com/finance/us/doc/convpdf/2019/11/13/13736052.pdf"
//        const val PDF_URL = "https://img.huxiucdn.com/pdf/Facebook.pdf?abc=234"
        const val PDF_URL = "https://file.finance.qq.com/finance/hs/pdf/2022/04/23/1213055132.PDF"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Constants.Pinch.MINIMUM_ZOOM = 1f
        Constants.Pinch.MAXIMUM_ZOOM = 2f

        binding.progressBar.show()
        thread {
            val url = URL(PDF_URL)
            val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            if (urlConnection.responseCode != 200) return@thread
            val inputStream = BufferedInputStream(urlConnection.inputStream);

            runOnUiThread {
                // binding.pdfView.setPageFling(false)
                binding.pdfView.maxZoom = 2f
                binding.pdfView.midZoom = 1.5f
                binding.pdfView.minZoom = 1f
                binding.pdfView
                    .fromStream(inputStream)
                    .enableDoubletap(true)
                    .pageSnap(false)
                    .onLoad { page: Int ->
                        binding.progressBar.hide()
                        Log.i("jthou", "onLoad page : $page")
                    }
                    .onRender { page: Int ->
                        Log.i("jthou", "onRender page : $page")
                    }
                    .onPageChange { page, _ ->
                        mPage = page
                        Log.i("jthou", "onPageChange page : $page")
                    }
                    .linkHandler {
                        // ignore not support jump
                    }
                    .load()
            }
        }

        binding.button.isEnabled = true
        binding.button.onClick {
            mSwitch = !mSwitch
            val fileName = if (mSwitch) PDF_FILE_NAME1 else PDF_FILE_NAME2
            binding.pdfView.fromAsset(fileName).load()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
//        val page = if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            Log.i("jthou", "横屏")
//            mPage - 1
//        } else {
//            Log.i("jthou", "竖屏")
//            mPage
//        }
        binding.pdfView.jumpTo(mPage)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.pdfView.recycle()
    }

}