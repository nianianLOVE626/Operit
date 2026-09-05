package com.ai.assistance.operit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moments")
data class MomentEntity(
    @PrimaryKey
    val id: String,
    val content: String,
    val imageUrls: String,  // JSON字符串，存储图片URL列表
    val authorType: String,
    val timestamp: Long,
    val isLikedByUser: Boolean = false,
    val isLikedByAi: Boolean = false
)