package com.ai.assistance.operit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moment_comments")
data class MomentCommentEntity(
    @PrimaryKey
    val id: String,
    val momentId: String,
    val content: String,
    val authorType: String, // "user" or "ai"
    val replyToCommentId: String? = null, // 回复哪条评论
    val delayMinutes: Int = 0, // 延迟几分钟后显示
    val timestamp: Long = System.currentTimeMillis(),
    val isVisible: Boolean = true // 延迟评论在时间到之前不可见
)