package com.jthou.pro

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.json.JSONObject

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.jthou.pro", appContext.packageName)
    }

    @Test
    fun jsonWrap() {
        val map = LinkedHashMap<String, String>()
        map["sec_tab_name"] = "行业动态"
        map["tab_name"] = "展开"
        map["industry_id"] = "1"
        map["fine_editing_id"] = "1436"
        map["page_position"] = "展开/收起"
        map["tracking_id"] = "f07f94c9e3f31f260d93a2b11383b062"
        println((map as Map<*, *>?)?.let { JSONObject(it).toString().replace("\\/", "/") })
    }

}
