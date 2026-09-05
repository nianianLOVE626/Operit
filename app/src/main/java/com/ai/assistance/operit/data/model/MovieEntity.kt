package com.ai.assistance.operit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val filePath: String,
    val duration: Long = 0L, // 毫秒
    val coverPath: String? = null,
    val addedAt: Long = System.currentTimeMillis(),
    val lastWatchedAt: Long? = null,
    val watchProgress: Long = 0L, // 观看进度（毫秒）
    val isFavorite: Boolean = false
)