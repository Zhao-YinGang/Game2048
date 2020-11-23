package com.zyg.game2048.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.NavHostFragment
import com.zyg.game2048.avtivity.MainActivity
import com.zyg.game2048.util.LogUtil


abstract class BaseNavFragment : Fragment() {

    companion object {
        private val log = LogUtil(BaseNavFragment::class.java, Log.VERBOSE)
    }

    abstract val layout: Int


    private val navController by lazy {
        NavHostFragment.findNavController(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        log.d("")
        /* 触摸事件的注册 */
        if (this is OnTouchListener) {
            (activity as? MainActivity)?.registerTouchListener(this)
        }
        return inflater.inflate(layout, container, false)
    }

    override fun onResume() {
        super.onResume()
        log.d("")
    }

    override fun onPause() {
        super.onPause()
        log.d("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        log.d("")
        if (this is OnTouchListener) {
            (this.activity as? MainActivity)?.unregisterTouchListener(this)
        }
    }

    fun navigate(
        @IdRes resId: Int,
        args: Bundle? = null,
        navOptions: NavOptions? = null,
        navigatorExtras: Navigator.Extras? = null
    ) {
        navController.navigate(resId, args, navOptions, navigatorExtras)
    }

    fun navigateUp() {
        navController.navigateUp()
    }
}