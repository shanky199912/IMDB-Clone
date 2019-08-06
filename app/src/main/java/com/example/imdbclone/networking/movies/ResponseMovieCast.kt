package com.example.imdbclone.networking.movies

import com.google.gson.annotations.SerializedName

data class ResponseMovieCast(

	@field:SerializedName("cast")
	val cast: ArrayList<CastItem?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("crew")
	val crew: ArrayList<CrewItem?>? = null
)