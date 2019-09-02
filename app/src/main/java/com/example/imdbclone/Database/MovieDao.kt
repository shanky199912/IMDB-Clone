package com.example.imdbclone.Database

import androidx.room.*

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovToFav(movie: Movie)

    @Delete
    fun removeMovieFromFav(movie: Movie)

    @Query("Select * from fav_movie_table where MovieId =:movieId")
    fun isMovieFav(movieId:Int):Boolean

    @Query("Select * from fav_movie_table")
    fun getFavMovie():List<Movie>
}