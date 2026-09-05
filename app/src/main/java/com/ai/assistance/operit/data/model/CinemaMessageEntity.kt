package com.ai.assistance.operit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cinema_messages")
data class CinemaMessageEntity(
    @PrimaryKey
    val id: String,
    val movieId: String? = null,
    val content: String,
    val authorType: String, // "user" or "ai"
    val timestamp: Long = System.currentTimeMillis(),
    val videoTimestamp: Long? = null // 消息发送时的视频播放位置
)