package com.ai.assistance.operit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coreading_messages")
data class CoReadingMessageEntity(
    @PrimaryKey
    val id: String,
    val bookId: String? = null,
    val content: String,
    val authorType: String, // "user" or "ai"
    val pageNumber: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val highlightText: String? = null // 引用的书中文字
)