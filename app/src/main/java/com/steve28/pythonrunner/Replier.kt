package com.steve28.pythonrunner

import android.app.Notification
import me.sungbin.kakaotalkbotbasemodule.library.KakaoBot

class Replier(
    private val bot: KakaoBot,
    private val action: Notification.Action
) {
    fun reply(msg: String) = bot.reply(action, msg)
    fun replyRoom(room: String, msg: String) = bot.replyRoom(room, msg)
}