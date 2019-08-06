package com.example.imdbclone.networking.Person

import com.google.gson.annotations.SerializedName

data class ResponseTvCredits(

    @field:SerializedName("cast")
    val castTv: List<CastItemTv?>? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("crew")
    val crewTv: List<CrewItemTv?>? = null
)