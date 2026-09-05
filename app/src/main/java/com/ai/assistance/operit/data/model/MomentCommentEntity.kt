package com.ai.assistance.operit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moment_comments")
data class MomentCommentEntity(
    @PrimaryKey
    val id: String,
    val momentId: String,
    val content: String,
    val authorType: String,
    val replyToCommentId: String? = null,
    val delayMinutes: Int = 0,
    val timestamp: Long,
    val isVisible: Boolean = true
)