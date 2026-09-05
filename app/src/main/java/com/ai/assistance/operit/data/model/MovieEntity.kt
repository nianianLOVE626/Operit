package com.ai.assistance.operit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val filePath: String,
    val duration: Long,
    val thumbnailPath: String?,
    val lastPosition: Long = 0  // 修复：改为 lastPosition
)