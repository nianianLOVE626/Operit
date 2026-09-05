package com.ai.assistance.operit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 朋友圈动态实体
 */
@Entity(tableName = "moments")
data class MomentEntity(
    @PrimaryKey
    val id: String,
    val content: String,
    val imageUrls: List<String> = emptyList(),
    val authorType: String, // "user" or "ai"
    val timestamp: Long = System.currentTimeMillis(),
    val likeCount: Int = 0,
    val isLikedByUser: Boolean = false,
    val isLikedByAi: Boolean = false
)

/**
 * 朋友圈评论实体
 */
@Entity(tableName = "moment_comments")
data class MomentCommentEntity(
    @PrimaryKey
    val id: String,
    val momentId: String,
    val content: String,
    val authorType: String, // "user" or "ai"
    val timestamp: Long = System.currentTimeMillis(),
    val replyToCommentId: String? = null,
    val delayMinutes: Int = 0 // 延迟回复时间（分钟）
)