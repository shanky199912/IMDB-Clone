package com.example.imdbclone.networking.Person

import com.google.gson.annotations.SerializedName

data class ResponseMovieCredits(

    @field:SerializedName("cast")
    val castMovie: List<CastItemMovie?>? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("crew")
    val crewMovie: List<CrewItemMovie?>? = null
)