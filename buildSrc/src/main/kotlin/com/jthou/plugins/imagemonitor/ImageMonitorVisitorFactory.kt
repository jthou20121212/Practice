package com.jthou.plugins.imagemonitor

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.jthou.plugins.logfilter.LogFilterClassVisitor
import org.objectweb.asm.ClassVisitor

import ImageMonitorParameters

abstract class ImageMonitorVisitorFactory : AsmClassVisitorFactory<ImageMonitorParameters> {

    override fun createClassVisitor(classContext: ClassContext, nextClassVisitor: ClassVisitor): ClassVisitor {
        return ImageMonitorClassVisitor(parameters.get().config.get(), nextClassVisitor)
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
        return true
    }

}