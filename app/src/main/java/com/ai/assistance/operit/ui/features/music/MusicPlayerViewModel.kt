package com.ai.assistance.operit.ui.features.music

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MusicPlayerViewModel(application: Application) : AndroidViewModel(application) {
    
    private val _playlist = MutableStateFlow<List<SongEntity>>(emptyList())
    val playlist: StateFlow<List<SongEntity>> = _playlist
    
    private val _currentSong = MutableStateFlow<SongEntity?>(null)
    val currentSong: StateFlow<SongEntity?> = _currentSong
    
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying
    
    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress
    
    init {
        loadPlaylist()
    }
    
    private fun loadPlaylist() {
        viewModelScope.launch {
            // TODO: 扫描本地音乐文件
            _playlist.value = listOf(
                SongEntity("1", "示例歌曲1", "艺术家1", "/sdcard/Music/song1.mp3", 180000),
                SongEntity("2", "示例歌曲2", "艺术家2", "/sdcard/Music/song2.mp3", 200000),
                SongEntity("3", "示例歌曲3", "艺术家3", "/sdcard/Music/song3.mp3", 220000)
            )
        }
    }
    
    fun playSong(song: SongEntity) {
        _currentSong.value = song
        _isPlaying.value = true
        _progress.value = 0f
    }
    
    fun togglePlay() {
        _isPlaying.value = !_isPlaying.value
    }
    
    fun previous() {
        val current = _currentSong.value ?: return
        val currentIndex = _playlist.value.indexOfFirst { it.id == current.id }
        if (currentIndex > 0) {
            playSong(_playlist.value[currentIndex - 1])
        }
    }
    
    fun next() {
        val current = _currentSong.value ?: return
        val currentIndex = _playlist.value.indexOfFirst { it.id == current.id }
        if (currentIndex < _playlist.value.size - 1) {
            playSong(_playlist.value[currentIndex + 1])
        }
    }
    
    fun seekTo(progress: Float) {
        _progress.value = progress
    }
}