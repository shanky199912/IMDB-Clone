package com.example.imdbclone.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_tvShow_table")
data class TvShows(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val TvShowId: Int?,
    @ColumnInfo(name = "Poster path of the show")
    val PosterPath: String?,
    @ColumnInfo(name = "Name of the show")
    val name: String?

)