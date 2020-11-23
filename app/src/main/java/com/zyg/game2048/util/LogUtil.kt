package com.zyg.game2048.util

import android.util.Log

class LogUtil constructor(
    private val tag: Any,
    private val level: Int
) {

    fun v(msg: Any?) {
        if (level <= Log.VERBOSE) {
            Log.v(getTag(), getMsgFormat(msg))
        }
    }

    fun d(msg: Any?) {
        if (level <= Log.DEBUG) {
            Log.d(getTag(), getMsgFormat(msg))
        }
    }

    fun i(msg: Any?) {
        if (level <= Log.INFO) {
            Log.i(getTag(), getMsgFormat(msg))
        }
    }


    fun w(msg: Any?) {
        if (level <= Log.WARN) {
            Log.w(getTag(), getMsgFormat(msg))
        }
    }

    @JvmOverloads
    fun e(msg: Any?, tr: Throwable? = null) {
        if (level <= Log.ERROR) {
            Log.w(getTag(), getMsgFormat(msg), tr)
        }
    }

    private fun getTag() =
        when (tag) {
            is String -> tag
            is Class<*> -> tag.simpleName
            else -> tag.javaClass.simpleName
        }

    /**
     * 获取类名,方法名,行号
     */
    private val format: String?
        get() {
            try {
                val sts = Thread.currentThread().stackTrace
                for (st in sts) {
                    if (st.isNativeMethod) {
                        continue
                    }
                    if (st.className == Thread::class.java.name) {
                        continue
                    }
                    if (st.className == LogUtil::class.java.name) {
                        continue
                    }
                    return ("Thread: " + Thread.currentThread().name +
                            ", " + st.className.substring(st.className.lastIndexOf(".") + 1)
                            + "." + st.methodName
                            + "(" + st.fileName + ":" + st.lineNumber + ")")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

    private fun getMsgFormat(msg: Any?): String {
        return "$format: ${msg?.toString() ?: "null"}"
    }
}