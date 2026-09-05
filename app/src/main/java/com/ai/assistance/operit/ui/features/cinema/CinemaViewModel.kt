package com.ai.assistance.operit.ui.features.cinema

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class CinemaViewModel(application: Application) : AndroidViewModel(application) {
    
    private val _movies = MutableStateFlow<List<MovieEntity>>(emptyList())
    val movies: StateFlow<List<MovieEntity>> = _movies
    
    private val _currentMovie = MutableStateFlow<MovieEntity?>(null)
    val currentMovie: StateFlow<MovieEntity?> = _currentMovie
    
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying
    
    private val _messages = MutableStateFlow<List<CinemaMessageEntity>>(emptyList())
    val messages: StateFlow<List<CinemaMessageEntity>> = _messages
    
    init {
        loadMovies()
    }
    
    private fun loadMovies() {
        viewModelScope.launch {
            // TODO: 扫描本地视频文件
            _movies.value = listOf(
                MovieEntity("1", "示例电影1", "/sdcard/Movies/movie1.mp4"),
                MovieEntity("2", "示例电影2", "/sdcard/Movies/movie2.mp4")
            )
        }
    }
    
    fun selectMovie(movie: MovieEntity) {
        _currentMovie.value = movie
        _isPlaying.value = false
    }
    
    fun togglePlay() {
        _isPlaying.value = !_isPlaying.value
    }
    
    fun sendMessage(content: String) {
        viewModelScope.launch {
            val message = CinemaMessageEntity(
                id = UUID.randomUUID().toString(),
                content = content,
                authorType = "user",
                timestamp = System.currentTimeMillis()
            )
            _messages.value = _messages.value + message
        }
    }
}