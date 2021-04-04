package com.steve28.pythonrunner

import android.app.Notification
import me.sungbin.kakaotalkbotbasemodule.library.KakaoBot

class Replier(
    private val bot: KakaoBot,
    private val action: Notification.Action
) {
    fun reply(msg: Any?) = bot.reply(action, msg.toString())
    fun replyRoom(room: String, msg: Any?) = bot.replyRoom(room, msg.toString())
}