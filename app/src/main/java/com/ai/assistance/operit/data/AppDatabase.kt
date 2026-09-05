package com.ai.assistance.operit.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import android.content.Context
import com.ai.assistance.operit.data.dao.*
import com.ai.assistance.operit.data.model.*

@Database(
    entities = [
        CustomEmojiEntity::class,
        MomentEntity::class,
        SongEntity::class,
        MovieEntity::class,
        CinemaMessageEntity::class,
        CoReadingMessageEntity::class,
        MomentCommentEntity::class
    ],
    version = 23,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    
    // 原有 DAO
    abstract fun customEmojiDao(): CustomEmojiDao
    abstract fun momentDao(): MomentDao
    
    // 新增功能的 DAO
    abstract fun songDao(): SongDao
    abstract fun movieDao(): MovieDao
    abstract fun cinemaMessageDao(): CinemaMessageDao
    abstract fun coReadingMessageDao(): CoReadingMessageDao
    abstract fun momentCommentDao(): MomentCommentDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "operit_database"
                )
                .addMigrations(
                    MIGRATION_22_23
                )
                .build()
                INSTANCE = instance
                instance
            }
        }
        
        private val MIGRATION_22_23 = object : Migration(22, 23) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // 1. 创建 songs 表
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS songs (
                        id TEXT PRIMARY KEY NOT NULL,
                        title TEXT NOT NULL,
                        artist TEXT NOT NULL,
                        filePath TEXT NOT NULL,
                        duration LONG NOT NULL,
                        coverArtPath TEXT
                    )
                """.trimIndent())
                
                // 2. 创建 movies 表
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS movies (
                        id TEXT PRIMARY KEY NOT NULL,
                        title TEXT NOT NULL,
                        filePath TEXT NOT NULL,
                        duration LONG NOT NULL,
                        thumbnailPath TEXT,
                        lastPosition LONG NOT NULL
                    )
                """.trimIndent())
                
                // 3. 创建 cinema_messages 表
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS cinema_messages (
                        id TEXT PRIMARY KEY NOT NULL,
                        movieId TEXT NOT NULL,
                        timestamp LONG NOT NULL,
                        content TEXT NOT NULL,
                        authorType TEXT NOT NULL
                    )
                """.trimIndent())
                
                // 4. 创建 coreading_messages 表
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS coreading_messages (
                        id TEXT PRIMARY KEY NOT NULL,
                        bookId TEXT NOT NULL,
                        timestamp LONG NOT NULL,
                        content TEXT NOT NULL,
                        authorType TEXT NOT NULL
                    )
                """.trimIndent())
                
                // 5. 创建 moment_comments 表
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS moment_comments (
                        id TEXT PRIMARY KEY NOT NULL,
                        momentId TEXT NOT NULL,
                        authorType TEXT NOT NULL,
                        content TEXT NOT NULL,
                        timestamp LONG NOT NULL,
                        replyToCommentId TEXT,
                        delayMinutes INTEGER NOT NULL,
                        isVisible INTEGER NOT NULL DEFAULT 1
                    )
                """.trimIndent())
                
                // 6. 为 moments 表添加新字段（如果还没有）
                try {
                    database.execSQL("ALTER TABLE moments ADD COLUMN imageUrls TEXT")
                } catch (e: Exception) {
                    // 字段已存在，忽略
                }
                
                try {
                    database.execSQL("ALTER TABLE moments ADD COLUMN isLikedByUser INTEGER NOT NULL DEFAULT 0")
                } catch (e: Exception) {
                    // 字段已存在，忽略
                }
                
                try {
                    database.execSQL("ALTER TABLE moments ADD COLUMN isLikedByAi INTEGER NOT NULL DEFAULT 0")
                } catch (e: Exception) {
                    // 字段已存在，忽略
                }
            }
        }
    }
}
