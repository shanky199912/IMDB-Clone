package com.example.imdbclone.networking

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

     const val API_KEY = "e3049387f1c52e0b75b406898b780872"

    //const val Path_Image = "https://image.tmdb.org/t/p/original"


    private val retrofitClient = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var service: TmdbService? = retrofitClient.create(TmdbService::class.java)

    fun <T> retrofitCallBack(fn: (Response<T>?, Throwable?) -> Unit): Callback<T> {
        return object : Callback<T> {
            override fun onFailure(call: Call<T>?, e: Throwable) = fn(null, e)

            override fun onResponse(call: Call<T>?, response: Response<T>?) = fn(response, null)


        }
    }
}

