package com.example.imdbclone.networking.TVshows.videos

import com.google.gson.annotations.SerializedName

data class ResponseVideoTv(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null
)