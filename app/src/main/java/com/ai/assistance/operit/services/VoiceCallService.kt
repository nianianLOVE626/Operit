package com.ai.assistance.operit.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.ai.assistance.operit.api.voice.VoiceServiceFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Lunaria语音通话服务
 * 实时TTS和语音识别
 */
class VoiceCallService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.Main + Job())
    private var isCallActive = false
    
    companion object {
        private const val ACTION_START_CALL = "START_CALL"
        private const val ACTION_END_CALL = "END_CALL"
        
        fun startCall(context: Context) {
            val intent = Intent(context, VoiceCallService::class.java).apply {
                action = ACTION_START_CALL
            }
            context.startService(intent)
        }
        
        fun endCall(context: Context) {
            val intent = Intent(context, VoiceCallService::class.java).apply {
                action = ACTION_END_CALL
            }
            context.startService(intent)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START_CALL -> startVoiceCall()
            ACTION_END_CALL -> endVoiceCall()
        }
        return START_STICKY
    }

    private fun startVoiceCall() {
        if (isCallActive) return
        isCallActive = true
        
        serviceScope.launch {
            // TODO: 初始化TTS
            // TODO: 初始化STT
            // TODO: 开始实时对话循环
        }
    }

    private fun endVoiceCall() {
        isCallActive = false
        // TODO: 清理资源
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        endVoiceCall()
        serviceScope.coroutineContext[Job]?.cancel()
    }
}