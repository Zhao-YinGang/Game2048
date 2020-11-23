package com.zyg.game2048.fragment

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import com.zyg.game2048.R
import com.zyg.game2048.game.Game2048
import com.zyg.game2048.util.LogUtil
import com.zyg.game2048.view.BaseNavFragment
import com.zyg.game2048.view.OnFlingGestureListener
import com.zyg.game2048.view.OnTouchListener

class GameFragment : BaseNavFragment(), OnTouchListener {

    companion object {
        private val log = LogUtil(GameFragment::class.java, Log.VERBOSE)
    }

    override val layout = R.layout.fragment_game


    private val gestureDetector: GestureDetector by lazy {
        GestureDetector(context, object : OnFlingGestureListener() {
            override fun onFlingUp(): Boolean {
                log.i("onFlingUp: ")
                Game2048.flingUp()
                return false
            }

            override fun onFlingDown(): Boolean {
                log.i("onFlingDown: ")
                Game2048.flingDown()
                return false
            }

            override fun onFlingLeft(): Boolean {
                log.i("onFlingLeft: ")
                Game2048.flingLeft()
                return false
            }

            override fun onFlingRight(): Boolean {
                log.i("onFlingRight: ")
                Game2048.flingRight()
                return false
            }

        })
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        Game2048.start(4)
        Game2048.setOnGameOverListener {
            log.i("Game Over")
        }
        Game2048.setOnUpdateDataListener { squares ->
            squares.forEachIndexed { index, rows ->
                log.i("$index" + rows.toList().toString())
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }
}