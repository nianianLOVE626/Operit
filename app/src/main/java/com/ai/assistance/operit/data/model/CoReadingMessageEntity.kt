package com.ai.assistance.operit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coreading_messages")
data class CoReadingMessageEntity(
    @PrimaryKey
    val id: String,
    val bookId: String,  // 注意：是bookId不是sessionId！
    val content: String,
    val authorType: String,
    val timestamp: Long
)