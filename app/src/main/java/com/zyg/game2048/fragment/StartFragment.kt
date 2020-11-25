package com.zyg.game2048.fragment

import android.os.Bundle
import com.zyg.game2048.R
import com.zyg.game2048.game.Game2048
import com.zyg.game2048.view.BaseNavFragment
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : BaseNavFragment() {

    override val layout = R.layout.fragment_start

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_start.setOnClickListener {
            navigate(
                StartFragmentDirections.actionStartFragmentToGameFragment().apply {
                    ranks = Game2048.DEFAULT_RANKS
                }
            )
        }
    }
}