package com.jthou.plugins.imagemonitor

import ImageMonitorConfig
import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 大图过滤插件
 *
 * Google 在 AGP 8.0 会将 Gradle Transform 给移除
 *
 * @author jthou
 * @since 1.0.0
 * @date 06-08-2023
 */
class ImageMonitorPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        // ExtensionContainer
        project.extensions.getByType(AndroidComponentsExtension::class.java).onVariants { variant ->
            variant.instrumentation.transformClassesWith(ImageMonitorVisitorFactory::class.java, InstrumentationScope.ALL) {
                it.config.set(ImageMonitorConfig())
            }
            variant.instrumentation.setAsmFramesComputationMode(FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS)
        }
    }

}