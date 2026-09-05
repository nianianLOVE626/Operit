package com.ai.assistance.operit.data.dao

import androidx.room.*
import com.ai.assistance.operit.data.model.CinemaMessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CinemaMessageDao {
    @Query("SELECT * FROM cinema_messages WHERE movieId = :movieId ORDER BY timestamp ASC")
    fun getMessagesByMovie(movieId: String): Flow<List<CinemaMessageEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: CinemaMessageEntity)
    
    @Query("DELETE FROM cinema_messages WHERE movieId = :movieId")
    suspend fun deleteMessagesByMovie(movieId: String)
    
    @Query("DELETE FROM cinema_messages")
    suspend fun deleteAll()
}