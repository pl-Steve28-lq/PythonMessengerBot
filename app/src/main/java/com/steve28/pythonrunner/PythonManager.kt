package com.steve28.pythonrunner

import android.content.Context

import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

class PythonManager(
    context: Context
) {
    private val python: Python
    init {
        if (!Python.isStarted()) Python.start(AndroidPlatform(context))
        python = Python.getInstance()
    }

    fun module(name: String) = python.getModule(name)
}