package com.ai.assistance.operit.ui.features.moments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ai.assistance.operit.data.model.MomentEntity
import com.ai.assistance.operit.data.repository.MomentRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MomentsViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository = MomentRepository.getInstance(application)
    
    val moments: StateFlow<List<MomentEntity>> = repository.getAllMoments()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    fun publishMoment(content: String, imageUrls: List<String>) {
        viewModelScope.launch {
            repository.publishMoment(content, imageUrls, "user")
        }
    }
    
    fun likeMoment(momentId: String, isUser: Boolean) {
        viewModelScope.launch {
            repository.likeMoment(momentId, isUser)
        }
    }
    
    fun addComment(momentId: String, content: String, delayMinutes: Int = 0) {
        viewModelScope.launch {
            repository.addComment(
                momentId = momentId,
                content = content,
                authorType = "user",
                delayMinutes = delayMinutes
            )
        }
    }
}