package com.ai.assistance.operit.ui.features.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ai.assistance.operit.ui.theme.BubbleTheme
import com.ai.assistance.operit.ui.theme.BubbleThemes

/**
 * 气泡皮肤选择界面
 */
@Composable
fun BubbleThemeSelector(
    currentThemeId: String,
    onThemeSelected: (BubbleTheme) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("聊天气泡皮肤") })
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(BubbleThemes.allThemes) { theme ->
                BubbleThemeCard(
                    theme = theme,
                    isSelected = theme.id == currentThemeId,
                    onClick = { onThemeSelected(theme) }
                )
            }
        }
    }
}

@Composable
fun BubbleThemeCard(
    theme: BubbleTheme,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onClick)
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 3.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.medium
                    )
                } else {
                    Modifier
                }
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            // 显示气泡预览
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // AI气泡
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(40.dp)
                        .clip(theme.shape)
                        .background(theme.aiBubbleColor)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "AI",
                        style = MaterialTheme.typography.bodySmall,
                        color = theme.aiTextColor
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // 用户气泡
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(40.dp)
                            .clip(theme.shape)
                            .background(theme.userBubbleColor)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "你",
                            style = MaterialTheme.typography.bodySmall,
                            color = theme.userTextColor
                        )
                    }
                }
            }
            
            // 主题名称
            Text(
                text = theme.name,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 4.dp),
                style = MaterialTheme.typography.labelSmall
            )
            
            // 选中标记
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "已选中",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}