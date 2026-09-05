package com.ai.assistance.operit.ui.features.cinema

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
 * Lunaria 本地影院系统
 * 一起看电影，实时聊天
 * 基于 open-watch-cinema 项目
 */
@Composable
fun CinemaScreen(
    viewModel: CinemaViewModel = viewModel()
) {
    val movies by viewModel.movies.collectAsState()
    val currentMovie by viewModel.currentMovie.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val messages by viewModel.messages.collectAsState()
    
    var showMovieSelector by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("本地影院") },
                actions = {
                    IconButton(onClick = { showMovieSelector = true }) {
                        Icon(Icons.Default.VideoLibrary, "选择电影")
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
            // 视频播放区域
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                if (currentMovie != null) {
                    // TODO: 集成视频播放器
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = currentMovie!!.title,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            IconButton(onClick = { viewModel.togglePlay() }) {
                                Icon(
                                    if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = if (isPlaying) "暂停" else "播放",
                                    modifier = Modifier.size(48.dp)
                                )
                            }
                        }
                    }
                } else {
                    Text(
                        text = "请选择电影",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            
            // 实时聊天区域
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "聊天室",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Divider()
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp)
                    ) {
                        items(messages) { msg ->
                            CinemaMessage(
                                message = msg,
                                isAi = msg.authorType == "ai"
                            )
                        }
                    }
                    // 输入框
                    var messageText by remember { mutableStateOf("") }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = messageText,
                            onValueChange = { messageText = it },
                            modifier = Modifier.weight(1f),
                            placeholder = { Text("说点什么...") }
                        )
                        IconButton(
                            onClick = {
                                if (messageText.isNotBlank()) {
                                    viewModel.sendMessage(messageText)
                                    messageText = ""
                                }
                            }
                        ) {
                            Icon(Icons.Default.Send, "发送")
                        }
                    }
                }
            }
        }
        
        if (showMovieSelector) {
            MovieSelectorDialog(
                movies = movies,
                onMovieSelected = { movie ->
                    viewModel.selectMovie(movie)
                    showMovieSelector = false
                },
                onDismiss = { showMovieSelector = false }
            )
        }
    }
}

@Composable
fun CinemaMessage(
    message: CinemaMessageEntity,
    isAi: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (isAi) Arrangement.Start else Arrangement.End
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = if (isAi)
                    MaterialTheme.colorScheme.secondaryContainer
                else
                    MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Text(
                text = message.content,
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun MovieSelectorDialog(
    movies: List<MovieEntity>,
    onMovieSelected: (MovieEntity) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("选择电影") },
        text = {
            LazyColumn {
                items(movies) { movie ->
                    ListItem(
                        headlineContent = { Text(movie.title) },
                        supportingContent = { Text(movie.path) },
                        modifier = Modifier.clickable { onMovieSelected(movie) }
                    )
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消")
            }
        }
    )
}

// 数据模型
data class MovieEntity(
    val id: String,
    val title: String,
    val path: String,
    val duration: Long = 0
)

data class CinemaMessageEntity(
    val id: String,
    val content: String,
    val authorType: String,
    val timestamp: Long
)