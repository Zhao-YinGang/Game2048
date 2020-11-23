package com.zyg.game2048.view

import android.view.MotionEvent

fun interface OnTouchListener {
    fun onTouchEvent(event: MotionEvent?): Boolean
}