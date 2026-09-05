package com.ai.assistance.operit.ui.features.chat.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ai.assistance.operit.data.model.CustomEmojiEntity

/**
 * 表情选择器对话框
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmojiPickerDialog(
    emojis: List<CustomEmojiEntity>,
    onEmojiSelected: (CustomEmojiEntity) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("recent") }
    
    val categories = listOf(
        "recent" to "⏱️ 最近",
        "happy" to "😊 开心",
        "love" to "❤️ 爱心",
        "sad" to "😢 难过",
        "angry" to "😠 生气",
        "cute" to "🥰 可爱",
        "surprise" to "😮 惊讶",
        "thinking" to "🤔 思考"
    )
    
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 500.dp)
            ) {
                // 标题
                Text(
                    text = "选择表情",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
                
                // 分类标签
                ScrollableTabRow(
                    selectedTabIndex = categories.indexOfFirst { it.first == selectedCategory },
                    modifier = Modifier.fillMaxWidth(),
                    edgePadding = 8.dp
                ) {
                    categories.forEach { (category, label) ->
                        Tab(
                            selected = selectedCategory == category,
                            onClick = { selectedCategory = category },
                            text = { Text(label) }
                        )
                    }
                }
                
                Divider()
                
                // 表情网格
                val filteredEmojis = when (selectedCategory) {
                    "recent" -> emojis.filter { it.lastUsedAt != null }
                        .sortedByDescending { it.lastUsedAt }
                        .take(20)
                    else -> emojis.filter { it.category == selectedCategory }
                        .sortedByDescending { it.useCount }
                }
                
                if (filteredEmojis.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "暂无表情\n点击 + 添加新表情",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        items(filteredEmojis) { emoji ->
                            AsyncImage(
                                model = emoji.imagePath,
                                contentDescription = "表情",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable {
                                        onEmojiSelected(emoji)
                                        onDismiss()
                                    }
                            )
                        }
                    }
                }
                
                Divider()
                
                // 底部按钮
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("取消")
                    }
                }
            }
        }
    }
}