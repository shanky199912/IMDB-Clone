package com.example.imdbclone.networking.TVshows

import com.google.gson.annotations.SerializedName

data class ResponseGenre(

	@field:SerializedName("genres")
	val genres: List<GenresItem?>? = null
)