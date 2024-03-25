package com.jthou.plugins.replacehandler

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class ReplaceHandlerPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.getByType(AndroidComponentsExtension::class.java).onVariants { variant ->
            variant.instrumentation.transformClassesWith(ReplaceHandlerVisitorFactory::class.java, InstrumentationScope.PROJECT) {

            }
            variant.instrumentation.setAsmFramesComputationMode(FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS)
        }
    }

}