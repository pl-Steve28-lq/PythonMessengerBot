package com.steve28.pythonmessengerbot

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chaquo.python.PyObject
import com.steve28.stedit.SteditField
import com.steve28.stedit.highlighter.PythonHighlighter

class MainActivity : ComponentActivity() {
    private val clsName = "Test"

    private val stedit by lazy {
        SteditField().apply {
            text = """
                from android.util import Log
                from android.widget import Toast 
                
                class $clsName:
                    def __init__(self, activity):
                        self.activity = activity
                        Log.d("Test", "Initialized")
                
                    def response(
                        self, sender, msg, room, isGroupChat, replier, profileImage, packageName
                    ):
                        if msg == "Hello":
                            Toast.makeText(self.activity, "Hello, Python!", Toast.LENGTH_LONG)
            """.trimIndent()
        }
    }
    private lateinit var response: PyObject
    private val getResponse by lazy { PythonManager.module["getResponse"]!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PythonManager.init(this)
        response = PythonManager.module["emptyFunction"]!!

        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Main()
                }
            }
        }
    }

    @Composable
    fun Main() {
        Column {
            stedit.Compose(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f),
                highlighter = PythonHighlighter
            )
            Utils.Space(16)
            Button({
                response = getResponse(stedit.text, clsName) {
                    Log.e("Python", it)
                } ?: raiseError()
                response.call("Sender", "Hello", "Test Room", true, 0, 0, 0)
            }) {
                Text("Launch!")
            }
        }
    }

    private fun raiseError(): PyObject {
        Toast.makeText(this, "Failed to launch the code.", Toast.LENGTH_LONG).show()
        return response
    }

    private fun getResponse(code: String, clsName: String, onError: (String) -> Unit)
        = getResponse.call(code, clsName, this, onError)
}