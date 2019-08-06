package com.example.imdbclone

data class MovieDataClass(

    val character: String,
    val name: String,
    val profile_path: String,
    val id: Int
)

data class Cast(val cast: ArrayList<MovieDataClass>)

data class Trailers(

    val key: String,
    val name: String
)

data class TrailerArray(val results: ArrayList<Trailers>)

data class Genres(

    val name: String
)


data class Overview(
    val original_title: String,
    val overview: String,
    val release_date: String,
    val runtime: Int,
    val poster_path: String,
    val vote_average: Float,
    val backdrop_path: String,
    val id: Int,
    val imdb_id: String,
    val genres: ArrayList<Genres>

)

data class Castinfo(
    val name: String,
    val biography: String,
    val place_of_birth: String,
    val birthday: String,
    val profile_path: String
)

data class Moviecast(
    val character: String,
    val poster_path: String,
    val title: String,
    val id: Int
)

data class Moviecastarray(val cast: ArrayList<Moviecast>)

data class Searchdetails(
    val title: String,
    val poster_path: String,
    val overview: String,
    val release_date: String,
    val id: Int
)

data class Searcharray(val results: ArrayList<Searchdetails>)
