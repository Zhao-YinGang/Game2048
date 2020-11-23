package com.zyg.game2048.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zyg.game2048.R
import com.zyg.game2048.view.BaseNavFragment
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : BaseNavFragment() {

    override val layout = R.layout.fragment_start

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_start.setOnClickListener {
            navigate(R.id.action_startFragment_to_gameFragment)
        }
    }
}