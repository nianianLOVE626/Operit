package com.ai.assistance.operit.data.dao

import androidx.room.*
import com.ai.assistance.operit.data.model.CustomEmojiEntity
import kotlinx.coroutines.flow.Flow

/**
 * 自定义表情数据访问对象
 */
@Dao
interface CustomEmojiDao {
    
    /** 插入表情 */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(emoji: CustomEmojiEntity): Long
    
    /** 批量插入 */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(emojis: List<CustomEmojiEntity>)
    
    /** 删除表情 */
    @Delete
    suspend fun delete(emoji: CustomEmojiEntity)
    
    /** 更新表情 */
    @Update
    suspend fun update(emoji: CustomEmojiEntity)
    
    /** 根据ID获取表情 */
    @Query("SELECT * FROM custom_emojis WHERE id = :id")
    suspend fun getById(id: Long): CustomEmojiEntity?
    
    /** 获取所有表情（按使用次数排序） */
    @Query("SELECT * FROM custom_emojis ORDER BY useCount DESC, addedAt DESC")
    fun getAllEmojis(): Flow<List<CustomEmojiEntity>>
    
    /** 根据分类获取表情 */
    @Query("SELECT * FROM custom_emojis WHERE category = :category ORDER BY useCount DESC")
    fun getEmojisByCategory(category: String): Flow<List<CustomEmojiEntity>>
    
    /** 获取收藏的表情 */
    @Query("SELECT * FROM custom_emojis WHERE isFavorite = 1 ORDER BY useCount DESC")
    fun getFavoriteEmojis(): Flow<List<CustomEmojiEntity>>
    
    /** 获取最近使用的表情 */
    @Query("SELECT * FROM custom_emojis WHERE lastUsedAt IS NOT NULL ORDER BY lastUsedAt DESC LIMIT :limit")
    fun getRecentEmojis(limit: Int = 20): Flow<List<CustomEmojiEntity>>
    
    /** 增加使用次数 */
    @Query("UPDATE custom_emojis SET useCount = useCount + 1, lastUsedAt = :timestamp WHERE id = :id")
    suspend fun incrementUseCount(id: Long, timestamp: Long = System.currentTimeMillis())
    
    /** 清空所有表情 */
    @Query("DELETE FROM custom_emojis")
    suspend fun deleteAll()
}