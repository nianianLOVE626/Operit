package com.ai.assistance.operit.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.ai.assistance.operit.R

/**
 * Lunaria通知栏快捷回复服务
 * 在通知栏直接回复消息
 */
class NotificationReplyService : Service() {
    
    companion object {
        private const val CHANNEL_ID = "lunaria_quick_reply"
        private const val NOTIFICATION_ID = 1001
        private const val KEY_TEXT_REPLY = "key_text_reply"
        private const val ACTION_REPLY = "com.ai.assistance.operit.ACTION_REPLY"
        
        fun showQuickReplyNotification(context: Context, aiMessage: String) {
            createNotificationChannel(context)
            
            val replyIntent = Intent(context, NotificationReplyService::class.java).apply {
                action = ACTION_REPLY
            }
            val replyPendingIntent = PendingIntent.getService(
                context,
                0,
                replyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
            
            val remoteInput = RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel("回复")
                .build()
            
            val replyAction = NotificationCompat.Action.Builder(
                R.drawable.ic_launcher_foreground,
                "回复",
                replyPendingIntent
            ).addRemoteInput(remoteInput).build()
            
            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("裴砚")
                .setContentText(aiMessage)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(replyAction)
                .setAutoCancel(true)
                .build()
            
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
        
        private fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    "快捷回复",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "通知栏快捷回复"
                }
                val notificationManager = context.getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_REPLY) {
            val remoteInput = RemoteInput.getResultsFromIntent(intent)
            val replyText = remoteInput?.getCharSequence(KEY_TEXT_REPLY)?.toString()
            
            if (!replyText.isNullOrBlank()) {
                handleQuickReply(replyText)
            }
        }
        return START_NOT_STICKY
    }

    private fun handleQuickReply(message: String) {
        // TODO: 将消息发送到聊天系统
        // TODO: 可以通过广播或直接调用ChatViewModel
        
        // 关闭通知
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(NOTIFICATION_ID)
    }
}