package com.ai.assistance.operit.data.dao

import androidx.room.*
import com.ai.assistance.operit.data.model.CoReadingMessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoReadingMessageDao {
    @Query("SELECT * FROM coreading_messages WHERE sessionId = :sessionId ORDER BY timestamp ASC")
    fun getMessagesBySession(sessionId: String): Flow<List<CoReadingMessageEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: CoReadingMessageEntity)
    
    @Query("DELETE FROM coreading_messages WHERE sessionId = :sessionId")
    suspend fun deleteMessagesBySession(sessionId: String)
    
    @Query("DELETE FROM coreading_messages")
    suspend fun deleteAll()
}