package com.ai.assistance.operit.data.repository

import com.ai.assistance.operit.data.dao.CustomEmojiDao
import com.ai.assistance.operit.data.model.CustomEmojiEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 自定义表情仓库
 */
@Singleton
class CustomEmojiRepository @Inject constructor(
    private val emojiDao: CustomEmojiDao
) {
    
    /** 获取所有表情 */
    fun getAllEmojis(): Flow<List<CustomEmojiEntity>> = emojiDao.getAllEmojis()
    
    /** 根据分类获取表情 */
    fun getEmojisByCategory(category: String): Flow<List<CustomEmojiEntity>> =
        emojiDao.getEmojisByCategory(category)
    
    /** 获取收藏的表情 */
    fun getFavoriteEmojis(): Flow<List<CustomEmojiEntity>> = emojiDao.getFavoriteEmojis()
    
    /** 获取最近使用的表情 */
    fun getRecentEmojis(limit: Int = 20): Flow<List<CustomEmojiEntity>> =
        emojiDao.getRecentEmojis(limit)
    
    /** 添加表情 */
    suspend fun addEmoji(
        imagePath: String,
        category: String = "happy",
        tags: String = ""
    ): Long {
        val emoji = CustomEmojiEntity(
            imagePath = imagePath,
            category = category,
            tags = tags
        )
        return emojiDao.insert(emoji)
    }
    
    /** 使用表情（增加使用次数） */
    suspend fun useEmoji(id: Long) {
        emojiDao.incrementUseCount(id)
    }
    
    /** 切换收藏状态 */
    suspend fun toggleFavorite(emoji: CustomEmojiEntity) {
        emojiDao.update(emoji.copy(isFavorite = !emoji.isFavorite))
    }
    
    /** 删除表情 */
    suspend fun deleteEmoji(emoji: CustomEmojiEntity) {
        emojiDao.delete(emoji)
    }
    
    /** 批量导入表情 */
    suspend fun importEmojis(emojis: List<CustomEmojiEntity>) {
        emojiDao.insertAll(emojis)
    }
}