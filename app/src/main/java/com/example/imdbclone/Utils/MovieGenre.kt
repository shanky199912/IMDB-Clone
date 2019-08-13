package com.example.imdbclone.Utils

import com.example.imdbclone.networking.movies.GenresItem

class MovieGenre {

    private val genreMap: HashMap<Int, String> = hashMapOf()
    private val genreMapTv:HashMap<Int,String> = hashMapOf()
    private val genre: ArrayList<GenresItem> = arrayListOf()

    fun isGenreListLoaded(): Boolean {
        return (true)
    }

    fun loadGenreList(genre: List<GenresItem?>?) {

        if (genre==null){
            return
        }

       genre.forEach {
           genreMap[it!!.id!!] = it.name!!.toString()
       }

    }

    fun loadGenreListTv(genre: List<com.example.imdbclone.networking.TVshows.GenresItem?>?) {

        genre!!.forEach {
            genreMapTv[it!!.id!!] = it.name!!.toString()
        }

    }

    fun getGenreName(genreId: Int): String? {

        return genreMap[genreId]
    }

    fun getGenreNameTv(genreId:Int): String? {

        return genreMapTv[genreId]
    }
}