package com.steve28.pythonrunner

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

import com.chaquo.python.PyObject
import de.markusressel.kodeeditor.library.view.CodeEditorLayout
import de.markusressel.kodehighlighter.language.python.PythonRuleBook
import me.sungbin.kakaotalkbotbasemodule.library.KakaoBot

class MainActivity : AppCompatActivity() {
    private val pm by lazy { PythonManager(this) }
    private val module by lazy { pm.module("execute") }
    private lateinit var func: PyObject
    private lateinit var bot: KakaoBot
    private var power = false

    val clsName = "Test"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bot = KakaoBot().init(applicationContext).setMessageReceiveListener {
            sender, message, room, isGroupChat, action, profileImage, packageName, bot ->
            func.call(sender, message, room, isGroupChat, Replier(bot, action), profileImage, packageName)
        }

        if (!bot.checkNotificationPermission())
            bot.requestReadNotification()

        val c = f<Button>(R.id.compile)
        val i = f<CodeEditorLayout>(R.id.input).apply {
            languageRuleBook = PythonRuleBook()
            editable = true
            text = """
                from numpy import array
                
                class $clsName:
                    def response(
                        self, sender, msg, room, isGroupChat, replier, profileImage, packageName
                    ):
                        if msg == "Hello": replier.reply(str(array([1, 2, 3])))
            """.trimIndent()
        }

        func = module["emptyFunction"]!!

        c.setOnClickListener {
            try {
                if (!power) {
                    func = getResponse(i.text, clsName) { s -> toast(this, s) }!!
                    snack(it, "Compile Success")
                }
                power = !power
                bot.setPower(power)
                c.text = if (power) "STOP" else "START"
            } catch (e: Exception)  { toast(this, e.toString()) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bot.onDestroy()
    }

    private fun <T: View> f(id: Int): T = findViewById(id)

    private fun getResponse(code: String, clsName: String, onError: (String) -> Unit)
        = module["getResponse"]?.call(code, clsName, this, onError)


    companion object {
        fun log(msg: Any?)
            = Log.d("TAG", msg.toString())

        fun toast(ctx: Context, msg: Any?)
            = Toast.makeText(ctx, msg.toString(), Toast.LENGTH_LONG).show()

        fun snack(view: View, msg: Any?)
            = Snackbar.make(view, msg.toString(), Snackbar.LENGTH_LONG).show()
    }
}
