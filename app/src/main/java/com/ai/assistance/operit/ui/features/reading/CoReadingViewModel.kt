package com.ai.assistance.operit.ui.features.reading

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ai.assistance.operit.data.model.CoReadingMessageEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class CoReadingViewModel(application: Application) : AndroidViewModel(application) {
    
    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage
    
    private val _totalPages = MutableStateFlow(100)
    val totalPages: StateFlow<Int> = _totalPages
    
    private val _messages = MutableStateFlow<List<CoReadingMessageEntity>>(emptyList())
    val messages: StateFlow<List<CoReadingMessageEntity>> = _messages
    
    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing
    
    private val _currentBookId = MutableStateFlow("")
    val currentBookId: StateFlow<String> = _currentBookId
    
    fun previousPage() {
        if (_currentPage.value > 1) {
            _currentPage.value--
            syncPage()
        }
    }
    
    fun nextPage() {
        if (_currentPage.value < _totalPages.value) {
            _currentPage.value++
            syncPage()
        }
    }
    
    fun sendComment(content: String) {
        viewModelScope.launch {
            val message = CoReadingMessageEntity(
                id = UUID.randomUUID().toString(),
                bookId = _currentBookId.value,  // ✅ 修复：使用bookId
                content = content,
                authorType = "user",
                timestamp = System.currentTimeMillis()
            )
            _messages.value = _messages.value + message
        }
    }
    
    private fun syncPage() {
        viewModelScope.launch {
            _isSyncing.value = true
            // TODO: 同步页面给AI
            kotlinx.coroutines.delay(500)
            _isSyncing.value = false
        }
    }
    
    fun openFile(filePath: String) {
        viewModelScope.launch {
            // 使用文件路径作为bookId
            _currentBookId.value = filePath
            
            // TODO: 打开PDF/EPUB文件
            // TODO: 解析总页数
        }
    }
}
