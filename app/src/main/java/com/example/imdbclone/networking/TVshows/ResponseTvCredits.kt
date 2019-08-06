package com.example.imdbclone.networking.TVshows

import com.google.gson.annotations.SerializedName

data class ResponseTvCredits(

    @field:SerializedName("cast")
    val cast: List<CastItem?>? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("crew")
    val crew: List<CrewItem?>? = null
)