package com.jthou.pro.crazy.handler

/**
 *
 *
 * @author jthou
 * @since 1.0.0
 * @date 15-03-2021
 */
fun main() {
    KLooper.prepare()
    val myLooper = KLooper.myLooper()
    val handler = object : KHandler(myLooper) {
        override fun handleMessage(msg: KMessage) {
            println(msg)
        }
    }
//    thread {
        for (i in 1..5) {
            for (j in 1..5) {
                val msg = KMessage()
                msg.what = i * 5 + j;
                msg.obj = "[$i, $j]"
                val `when` = (Math.random() * 1000).toLong()
                handler.sendMessageDelayed(msg, `when`)
            }
        }
        handler.sendMessageDelayed(KMessage(), 30000)
//    }
    KLooper.loop()

//    KLooper.prepare()
//    val myLooper = KLooper.myLooper()
//    val handler = object : KHandler(myLooper) {
//        override fun handleMessage(msg: KMessage) {
//            println(msg)
//        }
//    }
//    thread {
//        for (i in 1..5) {
//            if (i == 3) {
//                myLooper?.quit()
//            }
//            for (j in 1..5) {
//                val msg = KMessage()
//                msg.what = i * 5 + j;
//                msg.obj = "[$i, $j]"
//                val `when` = (Math.random() * 1000).toLong()
//                handler.sendMessageDelayed(msg, `when`)
//            }
//            Thread.sleep(2000)
//        }
//        handler.sendMessageDelayed(KMessage(), 30000)
//    }
//    KLooper.loop()
}


