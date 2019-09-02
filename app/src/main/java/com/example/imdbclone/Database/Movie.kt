package com.example.imdbclone.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_movie_table")
data class Movie(

    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    var MovieId: Int? = null,

    @ColumnInfo(name = "Poster path of Movies")
    var PosterPath: String? = "",

    @ColumnInfo(name = "name of the movies")
    var name: String? = ""
)