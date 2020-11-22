package com.zyg.game2048.view

import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import kotlin.math.abs

abstract class OnFlingGestureListener : GestureDetector.SimpleOnGestureListener() {

    abstract fun onFlingUp(): Boolean
    abstract fun onFlingDown(): Boolean
    abstract fun onFlingLeft(): Boolean
    abstract fun onFlingRight(): Boolean

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float) =
        if (abs(velocityY) > abs(velocityX)) {
            if (velocityY < 0) onFlingUp() else onFlingDown()
        } else {
            if (velocityX < 0) onFlingLeft() else onFlingRight()
        }
}