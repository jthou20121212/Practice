package com.jthou.pro.crazy

import android.os.Bundle
import android.util.Log
import android.view.View
import com.jthou.pro.xlog.DeleteLogActivity
import com.jthou.pro.xlog.XLogActivity
import com.popupwindow.PopupWindowActivity
import com.study.*
import com.study.asm.PrintMethodStackActivity
import com.study.breakpoint.continuingly.DownloadActivity
import com.study.constraintlayout.ConstraintLayoutActivity
import com.study.jsoup.JsoupActivity
import com.study.lifecycle.LifecycleActivity
import com.study.livedata.LiveDataActivity
import com.study.savedstate.SavedStateActivity
import com.study.snaphelper.LinearSnapHelperActivity
import com.study.snaphelper.PagerSnapHelperActivity
import com.study.snaphelper.ZhihuActivity
import com.study.viewbinding.ViewBindingActivity
import com.study.viewmodel.ViewModelActivity
import com.study.widget.ScaleableImageActivity
import splitties.activities.start

class MainActivity : SwipeDismissBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

//    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
//        return true
//    }

//    override fun onDestroy() {
//        Log.i("jthou", "before onDestroy")
//        super.onDestroy()
//        Log.i("jthou", "after onDestroy")
//
//        Glide.with(this).load("").fallback(R.drawable.fuck_my_life)
//            .into(object :CustomViewTarget<TextView, Drawable>(tv1){
//                override fun onLoadFailed(errorDrawable: Drawable?) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onResourceReady(
//                    resource: Drawable,
//                    transition: Transition<in Drawable>?
//                ) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onResourceCleared(placeholder: Drawable?) {
//                    TODO("Not yet implemented")
//                }
//
//            })
//        Glide.with(this).load("").submit().get()
//    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        Log.i("jthou", "onUserInteraction")
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.tv1 -> {
                VerticalStockActivity.launchActivity(this)
            }
            R.id.tv2 -> {
                ScrollActivity.launchActivity(this)
            }
            R.id.tv3 -> {
                RightScrollActivity.launchActivity(this)
            }
            R.id.tv4 -> {
                TestDensityActivity.launchActivity(this)
            }
            R.id.tv5 -> {
                TestViewActivity.launchActivity(this)
            }
            R.id.tv6 -> {
                PhoneInfoActivity.launchActivity(this)
            }
            R.id.tv7 -> TextViewActivity.launchActivity(this)
            R.id.tv8 -> start<PreviewPdfFileActivity>()
            R.id.tv10 -> start<MatrixActivity>()
            R.id.tv11 -> start<MessageMechanismActivity>()
            R.id.tv12 -> start<EventDispatchActivity>()
            R.id.tv13 -> start<MemberShakeActivity>()
            R.id.tv14 -> start<MemberLeakActivity>()
            R.id.tv15 -> start<LaunchAllowTaskReparentingActivity>()
            R.id.tv16 -> start<ParseALabelActivity>()
            R.id.tv17 -> start<ShadowActivity>()
            R.id.tv18 -> start<PrettyJsonActivity>()
            R.id.tv19 -> start<SparseArrayTestActivity>()
            R.id.tv20 -> start<ThreadPoolActivity> {}
            R.id.tv21 -> start<PopupWindowActivity> {}
            R.id.tv22 -> start<LineHeightActivity> {}
            R.id.tv23 -> start<ToastWindowManagerBadTokenExceptionActivity> {}
            R.id.tv24 -> start<GradientActivity> {}
            R.id.tv25 -> start<LinearSnapHelperActivity> {}
            R.id.tv26 -> start<PagerSnapHelperActivity> {}
            R.id.tv27 -> start<ScrollerTextViewActivity> {}
            R.id.tv28 -> start<ZhihuActivity> {}
            R.id.tv29 -> start<JavaTestActivity> {}
            R.id.tv30 -> start<ViewBindingActivity> {}
            R.id.tv31 -> start<DeleteLogActivity> {}
            R.id.tv32 -> start<XLogActivity> {}
            R.id.tv33 -> start<LifecycleActivity> {}
            R.id.tv34 -> start<LiveDataActivity> {}
            R.id.tv35 -> start<ViewModelActivity> {}
            R.id.tv36 -> start<SavedStateActivity> {}
            R.id.tv37 -> start<ConstraintLayoutActivity> {}
            R.id.tv38 -> start<ScaleableImageActivity> {}
            R.id.tv39 -> start<DownloadActivity> {}
            R.id.tv40 -> start<JsoupActivity> {}
            R.id.tv41 -> start<PrintMethodStackActivity> {}
        }
    }

}
