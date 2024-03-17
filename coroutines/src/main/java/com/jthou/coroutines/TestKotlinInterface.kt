package com.jthou.coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

interface TestKotlinInterface {


    public companion object Any : kotlin.Any()

    public fun handleException(context: CoroutineContext, exception: Throwable)

}