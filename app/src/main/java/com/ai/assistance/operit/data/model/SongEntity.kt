package com.ai.assistance.operit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class SongEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val artist: String,
    val filePath: String,
    val duration: Long, // 毫秒
    val album: String? = null,
    val coverArtPath: String? = null,
    val addedAt: Long = System.currentTimeMillis(),
    val playCount: Int = 0,
    val isFavorite: Boolean = false
)
