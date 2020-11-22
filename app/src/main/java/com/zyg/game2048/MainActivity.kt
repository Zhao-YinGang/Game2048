package com.zyg.game2048

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.zyg.game2048.game.Game2048
import com.zyg.game2048.view.OnFlingGestureListener

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val mGestureDetector: GestureDetector by lazy {
        GestureDetector(this, object : OnFlingGestureListener() {
            override fun onFlingUp(): Boolean {
                Log.i(TAG, "onFlingUp: ")
                Game2048.flingUp()
                return false
            }

            override fun onFlingDown(): Boolean {
                Log.i(TAG, "onFlingDown: ")
                Game2048.flingDown()
                return false
            }

            override fun onFlingLeft(): Boolean {
                Log.i(TAG, "onFlingLeft: ")
                Game2048.flingLeft()
                return false
            }

            override fun onFlingRight(): Boolean {
                Log.i(TAG, "onFlingRight: ")
                Game2048.flingRight()
                return false
            }

        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Game2048.start(4)
        Game2048.setOnGameOverListener {
            Log.i(TAG, "Game Over")
        }
        Game2048.setOnUpdateDataListener { squares ->
            squares.forEachIndexed { index, rows ->
                Log.i(TAG, "$index" + rows.toList().toString())
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return mGestureDetector.onTouchEvent(event)
    }
}