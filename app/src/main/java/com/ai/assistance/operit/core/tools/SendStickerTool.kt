package com.ai.assistance.operit.core.tools

import android.content.Context
import com.ai.assistance.operit.data.model.AITool
import com.ai.assistance.operit.data.model.ToolParameter
import com.ai.assistance.operit.data.repository.CustomEmojiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import org.json.JSONObject

object SendStickerTool {
    
    fun getDefinition(): AITool {
        return AITool(
            name = "send_sticker",
            description = "发送一个自定义表情包。可以根据情绪、分类或关键词选择合适的表情。",
            parameters = listOf(
                ToolParameter(
                    name = "category",
                    type = "string",
                    description = "表情分类：happy（开心）、sad（难过）、angry（生气）、love（喜欢）、surprised（惊讶）、tired（疲惫）、other（其他）",
                    required = false
                ),
                ToolParameter(
                    name = "keyword",
                    type = "string",
                    description = "表情关键词或描述",
                    required = false
                )
            )
        )
    }
    
    suspend fun execute(context: Context, parameters: JSONObject): String {
        return withContext(Dispatchers.IO) {
            try {
                val category = parameters.optString("category", "")
                val keyword = parameters.optString("keyword", "")
                
                val repository = CustomEmojiRepository.getInstance(context)
                
                val emojis = when {
                    category.isNotBlank() -> repository.getEmojisByCategory(category).firstOrNull()
                    keyword.isNotBlank() -> repository.searchEmojis(keyword).firstOrNull()
                    else -> repository.getAllEmojis().firstOrNull()
                } ?: emptyList()
                
                if (emojis.isEmpty()) {
                    return@withContext """{"success": false, "message": "没有找到匹配的表情包"}"""
                }
                
                val selected = emojis.maxByOrNull { it.useCount } ?: emojis.random()
                repository.incrementUseCount(selected.id)
                
                """{
                    "success": true,
                    "emoji_id": "${selected.id}",
                    "emoji_path": "${selected.filePath}",
                    "category": "${selected.category}",
                    "message": "已发送表情包"
                }"""
                
            } catch (e: Exception) {
                """{"success": false, "error": "${e.message}"}"""
            }
        }
    }
}