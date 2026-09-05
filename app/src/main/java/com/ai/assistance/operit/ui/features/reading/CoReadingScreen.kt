package com.ai.assistance.operit.ui.features.reading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

/**
 * Lunaria共读系统
 * 同步阅读PDF/EPUB，实时交流
 */
@Composable
fun CoReadingScreen(
    viewModel: CoReadingViewModel = viewModel()
) {
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val currentPage by viewModel.currentPage.collectAsState()
    val totalPages by viewModel.totalPages.collectAsState()
    val messages by viewModel.messages.collectAsState()
    val isSyncing by viewModel.isSyncing.collectAsState()
    
    var showCommentDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("共读") },
                actions = {
                    if (isSyncing) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    }
                    IconButton(onClick = { /* 打开文件选择 */ }) {
                        Icon(Icons.Default.FileOpen, "选择文件")
                    }
                }
            )
        },
        bottomBar = {
            CoReadingBottomBar(
                currentPage = currentPage,
                totalPages = totalPages,
                onPreviousPage = { viewModel.previousPage() },
                onNextPage = { viewModel.nextPage() },
                onComment = { showCommentDialog = true }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // 阅读内容区域
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                // TODO: 显示PDF/EPUB内容
                Text(
                    text = "第 $currentPage 页 / 共 $totalPages 页",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            
            // 实时评论区
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
                        text = "实时交流",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Divider()
                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp)
                    ) {
                        items(messages.size) { index ->
                            val msg = messages[index]
                            CoReadingMessage(
                                message = msg,
                                isAi = msg.authorType == "ai"
                            )
                        }
                    }
                }
            }
        }
        
        if (showCommentDialog) {
            CoReadingCommentDialog(
                onDismiss = { showCommentDialog = false },
                onSend = { comment ->
                    viewModel.sendComment(comment)
                    showCommentDialog = false
                }
            )
        }
    }
}

@Composable
fun CoReadingBottomBar(
    currentPage: Int,
    totalPages: Int,
    onPreviousPage: () -> Unit,
    onNextPage: () -> Unit,
    onComment: () -> Unit
) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onPreviousPage) {
                Icon(Icons.Default.ArrowBack, "上一页")
            }
            
            Text(
                text = "$currentPage / $totalPages",
                style = MaterialTheme.typography.bodyMedium
            )
            
            IconButton(onClick = onNextPage) {
                Icon(Icons.Default.ArrowForward, "下一页")
            }
            
            IconButton(onClick = onComment) {
                Icon(Icons.Default.ChatBubble, "评论")
            }
        }
    }
}

@Composable
fun CoReadingMessage(
    message: CoReadingMessageEntity,
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
            ),
            modifier = Modifier.widthIn(max = 250.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = message.content,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (message.pageNumber > 0) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "第${message.pageNumber}页",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun CoReadingCommentDialog(
    onDismiss: () -> Unit,
    onSend: (String) -> Unit
) {
    var comment by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("发表评论") },
        text = {
            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                placeholder = { Text("说点什么...") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (comment.isNotBlank()) {
                        onSend(comment)
                    }
                }
            ) {
                Text("发送")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消")
            }
        }
    )
}

// 共读消息实体
data class CoReadingMessageEntity(
    val id: String,
    val content: String,
    val authorType: String,
    val pageNumber: Int,
    val timestamp: Long
)