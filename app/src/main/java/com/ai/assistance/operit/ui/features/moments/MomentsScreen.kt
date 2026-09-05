package com.ai.assistance.operit.ui.features.moments

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.ai.assistance.operit.data.model.MomentEntity
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MomentsScreen(
    viewModel: MomentsViewModel = viewModel()
) {
    val moments by viewModel.moments.collectAsState()
    val context = LocalContext.current
    var showPublishDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("朋友圈") },
                actions = {
                    IconButton(onClick = { showPublishDialog = true }) {
                        Icon(Icons.Default.Add, "发朋友圈")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(moments) { moment ->
                MomentCard(
                    moment = moment,
                    onLike = { viewModel.likeMoment(moment.id, true) },
                    onComment = { /* TODO */ }
                )
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }

        if (showPublishDialog) {
            PublishMomentDialog(
                onDismiss = { showPublishDialog = false },
                onPublish = { content, imageUrls ->
                    viewModel.publishMoment(content, imageUrls)
                    showPublishDialog = false
                }
            )
        }
    }
}

@Composable
fun MomentCard(
    moment: MomentEntity,
    onLike: () -> Unit,
    onComment: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // 头像和名称
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = if (moment.authorType == "user") "念念" else "裴砚",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = formatTime(moment.timestamp),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 内容
            Text(
                text = moment.content,
                style = MaterialTheme.typography.bodyMedium
            )

            // 图片
            if (moment.imageUrls.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    moment.imageUrls.take(3).forEach { url ->
                        AsyncImage(
                            model = url,
                            contentDescription = null,
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 点赞和评论
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.clickable { onLike() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (moment.isLikedByUser) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "点赞",
                        tint = if (moment.isLikedByUser) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${moment.likeCount}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                IconButton(onClick = onComment) {
                    Icon(
                        imageVector = Icons.Default.ChatBubbleOutline,
                        contentDescription = "评论",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PublishMomentDialog(
    onDismiss: () -> Unit,
    onPublish: (String, List<String>) -> Unit
) {
    var content by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("发朋友圈") },
        text = {
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                placeholder = { Text("分享新鲜事...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (content.isNotBlank()) {
                        onPublish(content, emptyList())
                    }
                }
            ) {
                Text("发布")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("取消")
            }
        }
    )
}

private fun formatTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}