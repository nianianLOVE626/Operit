package com.ai.assistance.operit.data.dao

import androidx.room.*
import com.ai.assistance.operit.data.model.MomentCommentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MomentCommentDao {
    @Query("SELECT * FROM moment_comments WHERE momentId = :momentId ORDER BY timestamp ASC")
    fun getCommentsByMoment(momentId: String): Flow<List<MomentCommentEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: MomentCommentEntity)
    
    @Delete
    suspend fun deleteComment(comment: MomentCommentEntity)
    
    @Query("DELETE FROM moment_comments WHERE momentId = :momentId")
    suspend fun deleteCommentsByMoment(momentId: String)
    
    @Query("DELETE FROM moment_comments")
    suspend fun deleteAll()
}