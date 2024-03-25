package com.jthou.plugins.imagemonitor

import com.android.build.api.instrumentation.InstrumentationParameters
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import java.io.Serializable

interface ImageMonitorParameters : InstrumentationParameters {
    // - In plugin 'com.android.internal.version-check' type 'com.android.build.gradle.tasks.TransformClassesWithAsmTask' property 'visitorsList.$1.parameters.config' is missing an input or output annotation.
    @get:Input
    val config: Property<ImageMonitorConfig>
}

class ImageMonitorConfig(
    val src_class_name: String = "android/widget/ImageView",
    val dst_class_name: String = "com/study/asm/MonitorImageView",
    val weave_class_name: String = "com/study/asm/ImageMonitor",
    val weave_method_desc: String = "(Lcom.bumptech.glide.RequestManager;Ljava/lang/String;)Lcom.bumptech.glide.RequestBuilder"
) : Serializable