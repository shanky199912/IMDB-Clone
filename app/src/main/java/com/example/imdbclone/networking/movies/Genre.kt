package com.example.imdbclone.networking.movies

import com.google.gson.annotations.SerializedName

data class Genre(

	@field:SerializedName("genres")
	val genres: ArrayList<GenresItem?>? = null
)