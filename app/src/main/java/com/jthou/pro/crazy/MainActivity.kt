package com.jthou.pro.crazy

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.setPadding
import com.demo.PlateActivity
import com.demo.SimpleActivity
import com.gyf.immersionbar.ImmersionBar
import com.jthou.pro.crazy.databinding.ActivityMainBinding
import com.jthou.pro.crazy.eventdispatch.EventDispatchActivity
import com.jthou.pro.handler.BlockTestActivity
import com.jthou.pro.handler.MessageBarrierActivity
import com.jthou.pro.handler.MessagePrinterActivity
import com.jthou.pro.xlog.DeleteLogActivity
import com.jthou.pro.xlog.XLogActivity
import com.popupwindow.PopupWindowActivity
import com.study.DynamicSetMarginActivity
import com.study.GradientActivity
import com.study.JavaTestActivity
import com.study.LineHeightActivity
import com.study.NestedActivity
import com.study.PrettyJsonActivity
import com.study.ScrollerTextViewActivity
import com.study.ShadowActivity
import com.study.SparseArrayTestActivity
import com.study.ThreadPoolActivity
import com.study.ToastWindowManagerBadTokenExceptionActivity
import com.study.WechatPullScrollViewActivity
import com.study.WechatPullSmartRefreshLayoutActivity
import com.study.asm.ImageMonitorActivity
import com.study.asm.PrintMethodStackActivity
import com.study.attrs.CustomAttrsActivity
import com.study.breakpoint.continuingly.DownloadActivity
import com.study.clickarea.ExpandClickAreaActivity
import com.study.constraintlayout.ConstraintLayoutActivity
import com.study.coordinator.CoordinatorLayout1Activity
import com.study.coordinator.CoordinatorLayout2Activity
import com.study.coordinator.CoordinatorLayout3Activity
import com.study.coordinator.CoordinatorLayout4Activity
import com.study.jsoup.JsoupActivity
import com.study.lifecycle.LifecycleActivity
import com.study.livedata.LiveDataActivity
import com.study.savedstate.SavedStateActivity
import com.study.since290.IndustrySituationActivity
import com.study.snaphelper.LinearSnapHelperActivity
import com.study.snaphelper.PagerSnapHelperActivity
import com.study.snaphelper.ZhihuActivity
import com.study.viewbinding.ViewBindingActivity
import com.study.viewbinding.viewBinding
import com.study.viewmodel.ViewModelActivity
import com.study.widget.ScaleableImageActivity
import com.utils.dp
import splitties.views.onClick

data class ExampleItem(val name: String, val component: Class<out Activity>)
data class Example(val title: String, val data: List<ExampleItem>)

val examples = listOf(
    Example(
        title = "Android 消息机制",
        data = listOf(
            ExampleItem(name = "测试阻塞", component = BlockTestActivity::class.java),
            ExampleItem(name = "一些测试和验证", component = MessageBarrierActivity::class.java),
            ExampleItem(name = "同步屏障消息", component = MessageBarrierActivity::class.java),
            ExampleItem(name = "MessagePrinter", component = MessagePrinterActivity::class.java),
        )
    ),
    Example(
        title = "工作需求测试",
        data = listOf(
            ExampleItem(name = "XLog", component = XLogActivity::class.java),
            ExampleItem(name = "删除 XLog 文件", component = DeleteLogActivity::class.java),
            ExampleItem(name = "股票横竖屏", component = VerticalStockActivity::class.java),
            ExampleItem(name = "行业景气度", component = IndustrySituationActivity::class.java),
            ExampleItem(name = "查看 PDF 文件", component = PreviewPdfFileActivity::class.java),
            ExampleItem(
                name = "三段式（双层嵌套）",
                component = DynamicSetMarginActivity::class.java
            ),
            ExampleItem(
                name = "微信下拉效果（嵌套 ScrollView）",
                component = WechatPullScrollViewActivity::class.java
            ),
            ExampleItem(
                name = "微信下拉效果（嵌套 SmartRefreshLayout）",
                component = WechatPullSmartRefreshLayoutActivity::class.java
            ),
            ExampleItem(name = "有数", component = PlateActivity::class.java),
            ExampleItem(name = "控件通用自定义属性", component = CustomAttrsActivity::class.java),
            ExampleItem(
                name = "测试 TextView 行高属性",
                component = LineHeightActivity::class.java
            ),
            ExampleItem(name = "弹窗优先级", component = PopupWindowActivity::class.java),
            ExampleItem(name = "内存泄漏", component = MemberLeakActivity::class.java),
            ExampleItem(name = "内存抖动", component = MemberShakeActivity::class.java),
            ExampleItem(name = "自动缩小字号", component = TextViewActivity::class.java),
            ExampleItem(name = "我的自选", component = MyOptionalActivity::class.java)
        )
    ),
    Example(
        title = "CoordinatorLayout",
        data = listOf(
            ExampleItem(name = "一个示例", component = CoordinatorLayout1Activity::class.java),
            ExampleItem(name = "又一个示例", component = CoordinatorLayout2Activity::class.java),
            ExampleItem(name = "又又一个示例", component = CoordinatorLayout3Activity::class.java),
            ExampleItem(name = "又又又一个示例", component = CoordinatorLayout4Activity::class.java)
        )
    ),
    Example(
        title = "其他",
        data = listOf(
            ExampleItem(
                name = "ScrollView 嵌套 RecyclerView",
                component = NestedActivity::class.java
            ),
            ExampleItem(name = "扩大点击区域", component = ExpandClickAreaActivity::class.java),
            ExampleItem(name = "大图检测", component = ImageMonitorActivity::class.java),
            ExampleItem(name = "点击输出方法栈", component = PrintMethodStackActivity::class.java),
            ExampleItem(name = "Jsoup", component = JsoupActivity::class.java),
            ExampleItem(name = "断点续传", component = DownloadActivity::class.java),
            ExampleItem(name = "图片缩放", component = ScaleableImageActivity::class.java),
            ExampleItem(
                name = "ConstraintLayout",
                component = ConstraintLayoutActivity::class.java
            ),
            ExampleItem(name = "SavedState", component = SavedStateActivity::class.java),
            ExampleItem(name = "ViewModel", component = ViewModelActivity::class.java),
            ExampleItem(name = "LiveData", component = LiveDataActivity::class.java),
            ExampleItem(name = "Lifecycle", component = LifecycleActivity::class.java),
            ExampleItem(name = "ViewBinding", component = ViewBindingActivity::class.java),
            ExampleItem(name = "帧率", component = JavaTestActivity::class.java),
            ExampleItem(
                name = "可滚动的 TextView",
                component = ScrollerTextViewActivity::class.java
            ),
            ExampleItem(name = "线性渐变", component = GradientActivity::class.java),
            ExampleItem(
                name = "Toast 异常",
                component = ToastWindowManagerBadTokenExceptionActivity::class.java
            ),
            ExampleItem(
                name = "LinearSnapHelper",
                component = LinearSnapHelperActivity::class.java
            ),
            ExampleItem(name = "PagerSnapHelper", component = PagerSnapHelperActivity::class.java),
            ExampleItem(name = "自定义 SnapHelper", component = ZhihuActivity::class.java),
            ExampleItem(name = "线程池", component = ThreadPoolActivity::class.java),
            ExampleItem(name = "SparseArray", component = SparseArrayTestActivity::class.java),
            ExampleItem(name = "pretty json", component = PrettyJsonActivity::class.java),
            ExampleItem(name = "阴影", component = ShadowActivity::class.java),
            ExampleItem(name = "解析 a 标签", component = ParseALabelActivity::class.java),
            ExampleItem(
                name = "测试 allowTaskReparenting 属性",
                component = LaunchAllowTaskReparentingActivity::class.java
            ),
            ExampleItem(name = "事件分发", component = EventDispatchActivity::class.java),
            ExampleItem(name = "Matrix", component = MatrixActivity::class.java),
            ExampleItem(
                name = "绑定生命周期的 ValueAnimator",
                component = MatrixActivity::class.java
            ),
            ExampleItem(name = "手机信息", component = PhoneInfoActivity::class.java),
            ExampleItem(name = "测试 dp", component = TestDensityActivity::class.java),
            ExampleItem(name = "圆角控件动态变化", component = TestViewActivity::class.java),
            ExampleItem(name = "手势检测判断滑动方向", component = RightScrollActivity::class.java),
            ExampleItem(name = "为什么不能滚动", component = ScrollActivity::class.java),
            ExampleItem(name = "这是测试的啥", component = SimpleActivity::class.java),
        )
    )
)

class MainActivity : SwipeDismissBaseActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ImmersionBar.with(this)
            .statusBarColorInt(Color.WHITE)
            .statusBarDarkFont(true)
            .fitsSystemWindows(true)
            .init()

        examples.forEach { outer ->
            // 标题
            val titleTextView = TextView(this)
            titleTextView.textSize = 19f
            titleTextView.text = outer.title
            titleTextView.setPadding(10.dp, 10.dp, 0, 5.dp)
            titleTextView.setBackgroundColor(Color.parseColor("#efefef"))
            binding.container.addView(titleTextView)

            // 列表
            outer.data.forEachIndexed { index, inner ->
                val nameTextView = TextView(this)
                nameTextView.setPadding(10.dp)
                nameTextView.text = inner.name
                nameTextView.onClick {
                    val intent = Intent(this, inner.component)
                    startActivity(intent)
                }
                binding.container.addView(nameTextView)

                // 分隔线
                if (index < outer.data.size - 1) {
                    val dividerView = View(this)
                    dividerView.setBackgroundColor(Color.parseColor("#efefef"))
                    dividerView.layoutParams =
                        ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2.dp)
                    binding.container.addView(dividerView)
                }
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        Log.i("jthou", "onUserInteraction")
    }

}
