package com.ai.assistance.operit.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.avatarDataStore by preferencesDataStore(name = "avatar_settings")

/**
 * 头像管理Repository
 * 管理用户和AI的头像
 */
class AvatarManagementRepository private constructor(private val context: Context) {
    
    companion object {
        @Volatile
        private var INSTANCE: AvatarManagementRepository? = null
        
        private val USER_AVATAR_KEY = stringPreferencesKey("user_avatar")
        private val AI_AVATAR_KEY = stringPreferencesKey("ai_avatar")
        private val AVATAR_POOL_KEY = stringPreferencesKey("avatar_pool")
        
        fun getInstance(context: Context): AvatarManagementRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = AvatarManagementRepository(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }
    }
    
    val userAvatar: Flow<String?> = context.avatarDataStore.data.map { prefs ->
        prefs[USER_AVATAR_KEY]
    }
    
    val aiAvatar: Flow<String?> = context.avatarDataStore.data.map { prefs ->
        prefs[AI_AVATAR_KEY]
    }
    
    suspend fun setUserAvatar(avatarPath: String) {
        context.avatarDataStore.edit { prefs ->
            prefs[USER_AVATAR_KEY] = avatarPath
        }
    }
    
    suspend fun setAiAvatar(avatarPath: String) {
        context.avatarDataStore.edit { prefs ->
            prefs[AI_AVATAR_KEY] = avatarPath
        }
    }
    
    suspend fun addToAvatarPool(avatarPath: String) {
        context.avatarDataStore.edit { prefs ->
            val current = prefs[AVATAR_POOL_KEY] ?: ""
            val pool = if (current.isBlank()) {
                mutableListOf()
            } else {
                current.split(",").toMutableList()
            }
            if (!pool.contains(avatarPath)) {
                pool.add(avatarPath)
                prefs[AVATAR_POOL_KEY] = pool.joinToString(",")
            }
        }
    }
    
    fun getAvatarPool(): Flow<List<String>> = context.avatarDataStore.data.map { prefs ->
        val poolStr = prefs[AVATAR_POOL_KEY] ?: ""
        if (poolStr.isBlank()) {
            emptyList()
        } else {
            poolStr.split(",")
        }
    }
    
    suspend fun randomChangeAiAvatar() {
        val pool = getAvatarPool()
        // TODO: 从pool中随机选择一个头像
    }
}