package com.zyg.game2048.view

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.zyg.game2048.util.LogUtil

abstract class BaseActivity: AppCompatActivity() {

    companion object {
        private val log = LogUtil(BaseNavFragment::class.java, Log.VERBOSE)
    }

    abstract val layout: Int

    private val onTouchListeners: MutableList<OnTouchListener> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log.d("")
        setContentView(layout)
    }

    fun registerTouchListener(listener: OnTouchListener) {
        onTouchListeners.add(listener)
    }

    fun unregisterTouchListener(listener: OnTouchListener) {
        onTouchListeners.remove(listener)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        for (listener in onTouchListeners) {
            listener.onTouchEvent(ev)
        }
        return super.dispatchTouchEvent(ev)
    }
}