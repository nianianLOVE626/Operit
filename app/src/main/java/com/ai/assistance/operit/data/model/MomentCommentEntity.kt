package com.ai.assistance.operit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moment_comments")
data class MomentCommentEntity(
    @PrimaryKey
    val id: String,
    val momentId: String,
    val authorType: String,
    val content: String,
    val timestamp: Long,
    val replyToCommentId: String? = null,
    val delayMinutes: Int = 0,
    val isVisible: Int = 1  // 修复：改为 Int (0=false, 1=true)
)