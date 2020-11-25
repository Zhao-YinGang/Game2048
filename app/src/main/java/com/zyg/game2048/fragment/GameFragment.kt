package com.zyg.game2048.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zyg.game2048.R
import com.zyg.game2048.game.Game2048
import com.zyg.game2048.util.LogUtil
import com.zyg.game2048.view.BaseNavFragment
import com.zyg.game2048.view.OnFlingGestureListener
import com.zyg.game2048.view.OnTouchListener
import kotlinx.android.synthetic.main.fragment_game.*


class GameFragment : BaseNavFragment(), OnTouchListener {

    companion object {
        private val log = LogUtil(GameFragment::class.java, Log.VERBOSE)
    }

    override val layout = R.layout.fragment_game

    val ranks: Int by lazy {
        arguments?.let {
            GameFragmentArgs.fromBundle(it).ranks
        } ?: Game2048.DEFAULT_RANKS
    }

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

        recyclerview_gameGrid.apply {
            layoutManager = object : GridLayoutManager(context, ranks) {
                // 禁止recyclerview滑动
                override fun canScrollHorizontally() = false
                override fun canScrollVertically() = false
            }
            adapter = MyAdapter(ranks)
        }

        Game2048.start(ranks)
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

class MyAdapter(
    private val ranks: Int
) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemBg: ImageView = view.findViewById(R.id.item_bg)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_item_2048, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBg.setImageResource(R.drawable.item_2048_bg)
    }

    override fun getItemCount() = ranks * ranks
}