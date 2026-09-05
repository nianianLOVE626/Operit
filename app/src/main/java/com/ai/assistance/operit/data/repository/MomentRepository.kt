package com.ai.assistance.operit.data.repository

import android.content.Context
import com.ai.assistance.operit.data.dao.MomentDao
import com.ai.assistance.operit.data.db.AppDatabase
import com.ai.assistance.operit.data.model.MomentEntity
import com.ai.assistance.operit.data.model.MomentCommentEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class MomentRepository private constructor(private val momentDao: MomentDao) {
    
    companion object {
        @Volatile
        private var INSTANCE: MomentRepository? = null
        
        fun getInstance(context: Context): MomentRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = MomentRepository(
                    AppDatabase.getDatabase(context).momentDao()
                )
                INSTANCE = instance
                instance
            }
        }
    }
    
    fun getAllMoments(): Flow<List<MomentEntity>> = momentDao.getAllMoments()
    
    suspend fun getMomentById(momentId: String) = momentDao.getMomentById(momentId)
    
    suspend fun publishMoment(content: String, imageUrls: List<String>, authorType: String) {
        val moment = MomentEntity(
            id = UUID.randomUUID().toString(),
            content = content,
            imageUrls = imageUrls,
            authorType = authorType,
            timestamp = System.currentTimeMillis()
        )
        momentDao.insertMoment(moment)
    }
    
    suspend fun likeMoment(momentId: String, isUser: Boolean) {
        val moment = momentDao.getMomentById(momentId) ?: return
        val updated = if (isUser) {
            moment.copy(isLikedByUser = !moment.isLikedByUser)
        } else {
            moment.copy(isLikedByAi = !moment.isLikedByAi)
        }
        momentDao.updateMoment(updated)
    }
    
    fun getCommentsByMomentId(momentId: String): Flow<List<MomentCommentEntity>> {
        return momentDao.getCommentsByMomentId(momentId)
    }
    
    suspend fun addComment(
        momentId: String,
        content: String,
        authorType: String,
        replyToCommentId: String? = null,
        delayMinutes: Int = 0
    ) {
        val comment = MomentCommentEntity(
            id = UUID.randomUUID().toString(),
            momentId = momentId,
            content = content,
            authorType = authorType,
            replyToCommentId = replyToCommentId,
            delayMinutes = delayMinutes,
            timestamp = System.currentTimeMillis() + (delayMinutes * 60 * 1000)
        )
        momentDao.insertComment(comment)
    }
    
    suspend fun deleteMoment(moment: MomentEntity) = momentDao.deleteMoment(moment)
    
    suspend fun deleteComment(comment: MomentCommentEntity) = momentDao.deleteComment(comment)
}