package com.ai.assistance.operit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cinema_messages")
data class CinemaMessageEntity(
    @PrimaryKey
    val id: String,
    val movieId: String,
    val content: String,
    val authorType: String,
    val timestamp: Long
)