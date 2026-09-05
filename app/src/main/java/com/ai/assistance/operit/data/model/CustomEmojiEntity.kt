package com.ai.assistance.operit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 自定义表情实体
 */
@Entity(tableName = "custom_emojis")
data class CustomEmojiEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    /** 表情图片路径 */
    val imagePath: String,
    
    /** 表情分类：happy, love, sad, angry, cute, surprise, thinking */
    val category: String = "happy",
    
    /** 自定义标签 */
    val tags: String = "",
    
    /** 使用次数 */
    val useCount: Int = 0,
    
    /** 是否收藏 */
    val isFavorite: Boolean = false,
    
    /** 添加时间 */
    val addedAt: Long = System.currentTimeMillis(),
    
    /** 最后使用时间 */
    val lastUsedAt: Long? = null
)
