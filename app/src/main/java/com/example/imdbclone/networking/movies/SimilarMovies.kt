package com.example.imdbclone.networking.movies

import com.google.gson.annotations.SerializedName

data class SimilarMovies(

    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("total_pages")
    val totalPages: Int? = null,

    @field:SerializedName("results")
    val results: ArrayList<ResultsItem?>? = null,

    @field:SerializedName("total_results")
    val totalResults: Int? = null
)