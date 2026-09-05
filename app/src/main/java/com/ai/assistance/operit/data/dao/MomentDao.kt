package com.ai.assistance.operit.data.dao

import androidx.room.*
import com.ai.assistance.operit.data.model.MomentEntity
import com.ai.assistance.operit.data.model.MomentCommentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MomentDao {
    
    @Query("SELECT * FROM moments ORDER BY timestamp DESC")
    fun getAllMoments(): Flow<List<MomentEntity>>
    
    @Query("SELECT * FROM moments WHERE id = :momentId")
    suspend fun getMomentById(momentId: String): MomentEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoment(moment: MomentEntity)
    
    @Update
    suspend fun updateMoment(moment: MomentEntity)
    
    @Delete
    suspend fun deleteMoment(moment: MomentEntity)
    
    @Query("SELECT * FROM moment_comments WHERE momentId = :momentId ORDER BY timestamp ASC")
    fun getCommentsByMomentId(momentId: String): Flow<List<MomentCommentEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: MomentCommentEntity)
    
    @Delete
    suspend fun deleteComment(comment: MomentCommentEntity)
}