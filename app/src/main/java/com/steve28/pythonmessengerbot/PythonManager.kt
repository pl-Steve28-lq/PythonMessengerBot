package com.steve28.pythonmessengerbot

import android.content.Context
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

object PythonManager {
    private lateinit var python: Python
    lateinit var module: PyObject

    fun init(context: Context) {
        if (!Python.isStarted()) Python.start(AndroidPlatform(context))
        python = Python.getInstance()
        module = python.getModule("execute")
    }
}