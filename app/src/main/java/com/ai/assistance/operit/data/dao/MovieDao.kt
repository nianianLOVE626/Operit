package com.ai.assistance.operit.data.dao

import androidx.room.*
import com.ai.assistance.operit.data.model.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    
    @Query("SELECT * FROM movies ORDER BY title ASC")
    fun getAllMovies(): Flow<List<MovieEntity>>
    
    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovieById(movieId: String): MovieEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)
    
    @Update
    suspend fun updateMovie(movie: MovieEntity)
    
    @Delete
    suspend fun deleteMovie(movie: MovieEntity)
    
    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()
}