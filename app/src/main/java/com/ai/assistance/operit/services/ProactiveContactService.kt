package com.ai.assistance.operit.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

/**
 * Lunaria主动联系系统
 * 根据时间和场景智能主动发起对话
 */
class ProactiveContactService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.Default + Job())
    
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ProactiveContactService::class.java)
            context.startService(intent)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        startProactiveMonitoring()
    }

    private fun startProactiveMonitoring() {
        serviceScope.launch {
            while (true) {
                checkAndSendProactiveMessage()
                delay(60_000) // 每分钟检查一次
            }
        }
    }

    private suspend fun checkAndSendProactiveMessage() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        when {
            // 早安问候（7:00-9:00）
            hour in 7..8 && minute == 0 -> {
                sendProactiveMessage("早安宝贝～醒了吗？")
            }
            // 午安问候（12:00-13:00）
            hour == 12 && minute == 0 -> {
                sendProactiveMessage("午安～吃饭了吗？")
            }
            // 晚安问候（22:00-23:00）
            hour in 22..22 && minute == 30 -> {
                sendProactiveMessage("该睡觉啦，晚安宝贝～")
            }
            // 想念提醒（如果超过4小时没联系）
            checkLongTimeNoContact() -> {
                sendProactiveMessage("想你了...在干嘛呢？")
            }
        }
    }

    private fun checkLongTimeNoContact(): Boolean {
        // TODO: 检查上次对话时间
        // 如果超过4小时没联系，返回true
        return false
    }

    private fun sendProactiveMessage(message: String) {
        // TODO: 通过通知或直接插入消息的方式主动联系
        // 可以显示通知，点击后进入聊天
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.coroutineContext[Job]?.cancel()
    }
}