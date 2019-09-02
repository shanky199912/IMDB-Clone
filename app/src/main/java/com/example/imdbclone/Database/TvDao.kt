package com.example.imdbclone.Database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.imdbclone.networking.TVshows.ResultsItem

@Dao
interface TvDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTvToFav(shows: TvShows)

    @Delete
    fun removeShowFromFav(shows: TvShows)

    /*@Query("Select * from fav_tvShow_table where TvShowId=:tvShowId")
    fun isShowFav(tvShowId: Int): Boolean*/

    @Query("Select * from fav_tvShow_table")
    fun getFavTv(): List<TvShows>
}