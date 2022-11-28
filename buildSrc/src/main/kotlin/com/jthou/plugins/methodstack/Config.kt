package com.jthou.plugins.methodstack

import com.android.build.api.instrumentation.InstrumentationParameters
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import java.io.Serializable


// 如果想为任意方法打印方法栈则使用此注解
@Retention
@Target(AnnotationTarget.FUNCTION)
annotation class PrintMethodStack

// 如果任意方法不想打印方法栈使用此注解
// 如果两个注解都使用则不打印
@Retention
@Target(AnnotationTarget.FUNCTION)
annotation class IgnoreMethodStack

interface PrintMethodStackParameters : InstrumentationParameters {
    // - In plugin 'com.android.internal.version-check' type 'com.android.build.gradle.tasks.TransformClassesWithAsmTask' property 'visitorsList.$1.parameters.config' is missing an input or output annotation.
    @get:Input
    val config: Property<PrintMethodStackConfig>
}

class PrintMethodStackHookPoint(
    val interfaceName: String,
    val methodName: String,
    val methodDesc: String,
) : Serializable {
    val interfaceSignSuffix = "L$interfaceName;"
}

// Cannot fingerprint input property 'visitorsList.$1.parameters.config': value 'com.jthou.plugins.methodstack.PrintMethodStackConfig@4b5fddb5' cannot be serialized.
class PrintMethodStackConfig(
    val printMethodStackClass: String = "com.study.asm.PrintMethodStack",
    val printMethodStackMethodName: String = "printMethodStack",
    val printMethodStackAnnotation: String = "com.jthou.plugins.methodstack.PrintMethodStack",
    val ignoreMethodStackAnnotation: String = "com.jthou.plugins.methodstack.IgnoreMethodStack",
    val hookPointList: List<PrintMethodStackHookPoint> = extraHookPoints
) : Serializable {
}

private val extraHookPoints = listOf(
    PrintMethodStackHookPoint(
        interfaceName = "android/view/View\$OnClickListener",
        methodName = "onClick",
        methodDesc = "onClick(Landroid/view/View;)V"
    ),
    PrintMethodStackHookPoint(
        interfaceName = "android/view/View\$OnLongClickListener",
        methodName = "onLongClick",
        methodDesc = "onLongClick(Landroid/view/View;)V"
    ),
    PrintMethodStackHookPoint(
        interfaceName = "com/chad/library/adapter/base/listener/OnItemClickListener",
        methodName = "onItemClick",
        methodDesc = "onItemClick(Lcom/chad/library/adapter/base/BaseQuickAdapter;Landroid/view/View;I)V"
    ),
    PrintMethodStackHookPoint(
        interfaceName = "com/chad/library/adapter/base/listener/OnItemChildClickListener",
        methodName = "onItemChildClick",
        methodDesc = "onItemChildClick(Lcom/chad/library/adapter/base/BaseQuickAdapter;Landroid/view/View;I)V",
    )
)