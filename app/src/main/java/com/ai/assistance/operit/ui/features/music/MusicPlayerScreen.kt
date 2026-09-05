package com.ai.assistance.operit.ui.features.music

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Lunaria 音乐播放器
 * 基于 music-player-mcp 项目
 * 支持音频代理和本地播放
 */
@Composable
fun MusicPlayerScreen(
    viewModel: MusicPlayerViewModel = viewModel()
) {
    val playlist by viewModel.playlist.collectAsState()
    val currentSong by viewModel.currentSong.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val progress by viewModel.progress.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("音乐播放器") },
                actions = {
                    IconButton(onClick = { /* 打开播放列表 */ }) {
                        Icon(Icons.Default.QueueMusic, "播放列表")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // 当前播放歌曲信息
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 封面
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MusicNote,
                            contentDescription = "封面",
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.Center),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // 歌曲信息
                    Text(
                        text = currentSong?.title ?: "未播放",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = currentSong?.artist ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // 进度条
                    Slider(
                        value = progress,
                        onValueChange = { viewModel.seekTo(it) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(formatTime(progress * (currentSong?.duration ?: 0)))
                        Text(formatTime(currentSong?.duration ?: 0))
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // 播放控制
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { viewModel.previous() }) {
                            Icon(
                                Icons.Default.SkipPrevious,
                                contentDescription = "上一首",
                                modifier = Modifier.size(36.dp)
                            )
                        }
                        
                        FilledIconButton(
                            onClick = { viewModel.togglePlay() },
                            modifier = Modifier.size(64.dp)
                        ) {
                            Icon(
                                if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isPlaying) "暂停" else "播放",
                                modifier = Modifier.size(36.dp)
                            )
                        }
                        
                        IconButton(onClick = { viewModel.next() }) {
                            Icon(
                                Icons.Default.SkipNext,
                                contentDescription = "下一首",
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }
            }
            
            // 播放列表
            Text(
                text = "播放列表",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.titleMedium
            )
            
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(playlist) { song ->
                    SongItem(
                        song = song,
                        isPlaying = song.id == currentSong?.id,
                        onClick = { viewModel.playSong(song) }
                    )
                }
            }
        }
    }
}

@Composable
fun SongItem(
    song: SongEntity,
    isPlaying: Boolean,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = { Text(song.title) },
        supportingContent = { Text(song.artist) },
        trailingContent = {
            if (isPlaying) {
                Icon(
                    Icons.Default.VolumeUp,
                    contentDescription = "正在播放",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        modifier = Modifier.clickable(onClick = onClick)
    )
}

private fun formatTime(milliseconds: Long): String {
    val seconds = milliseconds / 1000
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%d:%02d", minutes, remainingSeconds)
}

// 数据模型
data class SongEntity(
    val id: String,
    val title: String,
    val artist: String,
    val path: String,
    val duration: Long = 0
)