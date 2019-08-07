package com.example.imdbclone.Utils

import com.example.imdbclone.networking.movies.GenresItem

class MovieGenre {

    private val genreMap: HashMap<Int, String> = hashMapOf()
    private val genre: ArrayList<GenresItem> = arrayListOf()

    fun isGenreListLoaded(): Boolean {
        return (true)
    }

    fun loadGenreList(genre: ArrayList<GenresItem?>?) {

       genre!!.forEach {
           genreMap[it!!.id!!] = it.name!!.toString()
       }

    }

    fun getGenreName(genreId: Int): String? {

        return genreMap[genreId]
    }
}