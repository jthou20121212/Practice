package com.jthou.pro.crazy

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.util.Printer
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ThreadUtils
import com.jthou.pro.crazy.R
import com.jthou.pro.crazy.handler.Idler
import java.util.*

class MessageMechanismActivity : AppCompatActivity() {

    private var handler: Handler? = null
    private var message: Message? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_mechanism)
//        thread {
//            Log.i("logTag", "prepare")
//            Looper.prepare()
//            handler = object : Handler(Looper.myLooper()!!) {
//                override fun handleMessage(msg: Message) {
//                    super.handleMessage(msg)
//                }
//            }
//            handler?.post {
//                Log.i("logTag", "isMainThread : " + ThreadUtils.isMainThread())
//            }
//            Log.i("logTag", "post")
//            Looper.loop()
//            Log.i("logTag", "loop")
//        }
//        message = Message.obtain()
//
//        val mainHandler = Handler()
//        mainHandler.postDelayed(Runnable {
//            handler?.sendMessage(message)
//            handler?.sendMessage(message)
//        }, 1000)

//        thread {
//            val handlerThread = HandlerThread("HandlerThread")
//            handlerThread.start()
//            val handler = Handler(handlerThread.looper)
//            handler.post {
//                LogUtils.i("jthou", "1")
//                LogUtils.i("jthou", "2")
//                LogUtils.i("jthou", "3")
//                LogUtils.i("jthou", "isMainThread : " + ThreadUtils.isMainThread())
//            }
//            handler.post {
//                LogUtils.i("jthou", "11")
//                LogUtils.i("jthou", "22")
//                LogUtils.i("jthou", "33")
//            }
//        }

//        thread {
//            Looper.prepare()
//            val myLooper = Looper.myLooper()
//            LogUtils.i("jthou", "myLooper : $myLooper")
//            myLooper?.let {
//                val handler = object : Handler(myLooper) {
//                    override fun handleMessage(msg: Message) {
//                        println(msg)
//                    }
//                }
//                thread {
//                    for (i in 1..5) {
//                        Thread.sleep(2000)
//                        for (j in 1..5) {
//                            val msg = Message.obtain()
//                            msg.what = i * 5 + j;
//                            msg.obj = "[$i, $j]"
//                            handler.sendMessageDelayed(msg, (Math.random() * 10000).toLong())
//                        }
//                    }
//                }
//            }
//            Looper.loop()
//        }


//        handler = Handler(object : Handler.Callback {
//            override fun handleMessage(msg: Message): Boolean {
//                LogUtils.i("jthou", "msg.callback : ${msg.callback}")
//                LogUtils.i("jthou", "mTestRunnable : $mTestRunnable")
//                return true;
//            }
//        })
//        handler?.post(mTestRunnable)

//        val msg1 = Message.obtain()
//        handler?.sendMessageDelayed(msg, 1000)
//        handler?.sendMessageDelayed(msg, 1000)

//        thread {
//            Looper.prepare()
//            val handler = Handler(object : Handler.Callback {
//                override fun handleMessage(msg: Message): Boolean {
//                    LogUtils.i("jthou", "msg : $msg")
//                    return false
//                }
//            })
//            for (i in 0..5) {
//                val msg = Message.obtain()
//                msg.obj = "这是一堆非延时消息"
//                handler.sendMessage(msg)
//            }
//            for (i in 0..10) {
//                val msg = Message.obtain()
//                msg.obj = "obj : $i"
//                val `when` = i * 100L
//                handler.sendMessageDelayed(msg, `when`)
////                handler.sendMessage(msg)
//            }
////            Looper.myLooper()?.quit()
//            Looper.myLooper()?.quitSafely()
//            Looper.loop()
//        }

        val handlerThread = HandlerThread("test thread")
        handlerThread.start()
        val handler = Handler(handlerThread.looper, object : Handler.Callback {
            override fun handleMessage(msg: Message): Boolean {
                Log.i("jthou", "isMainThread : ${ThreadUtils.isMainThread()}")
                return false
            }
        })
        for (i in 0 until 5) {
            handler.sendEmptyMessage(i)
        }

        val idler = Idler()
        Looper.getMainLooper().queue.addIdleHandler(idler)
        handler.post {
            // nothing, just active
        }
        idler.waitForIdle()

        handler.sendMessageDelayed(Message.obtain(), 1000)

        Looper.getMainLooper().setMessageLogging {
            Log.i("jthou", it)
        }
    }

    private val mTestRunnable = object : Runnable {
        override fun run() {
            LogUtils.i("jthou", "mTestRunnable : $this")
            handler?.postDelayed(this, 1000)
        }
    }

}