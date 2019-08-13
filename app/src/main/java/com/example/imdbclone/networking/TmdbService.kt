package com.example.imdbclone.networking

import com.example.imdbclone.networking.Person.PersonDetail
import com.example.imdbclone.networking.Person.ResponseMovieCredits
import com.example.imdbclone.networking.Person.ResponseTvCredits
import com.example.imdbclone.networking.Search.ResponseSearch
import com.example.imdbclone.networking.TVshows.*
import com.example.imdbclone.networking.TVshows.videos.ResponseVideoTv
import com.example.imdbclone.networking.movies.*
import com.example.imdbclone.networking.movies.ResponsePopular
import com.example.imdbclone.networking.movies.ResponseTopRated
import com.example.imdbclone.networking.movies.videos.VideoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbService {

    //Movie
    @GET("movie/now_playing")
    fun listNowShowing(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): Call<ResponseNowShowing>

    @GET("movie/popular")
    fun listPopular(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): Call<ResponsePopular>

    @GET("movie/upcoming")
    fun listUpcoming(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): Call<ResponseUpcoming>

    @GET("movie/top_rated")
    fun listTopRated(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): Call<ResponseTopRated>

    @GET("movie/{id}")
    fun getMovieDetails(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<Movie>


    @GET("movie/{id}/videos")
    fun getMovieVideos(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<VideoResponse>


    @GET("movie/{id}/credits")
    fun getMovieCast(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<ResponseMovieCast>

    @GET("movie/{id}/similar")
    fun getSimilarMovies(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<SimilarMovies>

    @GET("genre/movie/list")
    fun getMovieGenreList(
        @Query("api_key") apiKey: String,
        @Query("lang") lang: String
    ): Call<Genre>

    //Person
    @GET("person/{person_id}")
    fun getPersonDetail(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String
    ): Call<PersonDetail>

    @GET("person/{person_id}/movie_credits")
    fun getMovieCreditsOfPerson(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String
    ): Call<ResponseMovieCredits>

    @GET("person/{person_id}/tv_credits")
    fun getTvCreditsOfPerson(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String
    ): Call<ResponseTvCredits>

    //TV Shows
    @GET("tv/airing_today")
    fun listAiringToday(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<ResponseTvAiringToday>

    @GET("tv/on_the_air")
    fun listOnTheAir(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<ResponseOnTheAir>

    @GET("tv/popular")
    fun listPopular(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<com.example.imdbclone.networking.TVshows.ResponsePopular>

    @GET("tv/top_rated")
    fun listTopRated(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<com.example.imdbclone.networking.TVshows.ResponseTopRated>

    @GET("tv/{tv_id}/videos")
    fun getTvVideos(
        @Path("tv_id") id: Int,
        @Query("api_key") apiKey: String
    ): Call<ResponseVideoTv>

    @GET("tv/{tv_id}/credits")
    fun getTvCredits(
        @Path("tv_id") id: Int,
        @Query("api_key") apiKey: String
    ): Call<com.example.imdbclone.networking.TVshows.ResponseTvCredits>

    @GET("tv/{tv_id}/similar")
    fun getTvSimilar(
        @Path("tv_id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<ResponseSimilarTv>

    @GET("tv/{tv_id}")
    fun getTvDetails(
        @Path("tv_id") id: Int,
        @Query("api_key") apiKey: String
    ): Call<ResponseTvDetails>

    @GET("genre/movie/list")
    fun getTvGenreList(
        @Query("api_key") apiKey: String,
        @Query("lang") lang: String
    ): Call<ResponseGenre>

    //Search
    @GET("search/multi")
    fun listSearch(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Call<ResponseSearch>
}