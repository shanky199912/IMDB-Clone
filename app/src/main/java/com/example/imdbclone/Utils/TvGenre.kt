package com.example.imdbclone.Utils

class TvGenre {

    private val genreMapTv:HashMap<Int,String> = hashMapOf()

    fun isGenreListLoadedTv(): Boolean {
        return (true)
    }

    fun loadGenreListTv(genre: List<com.example.imdbclone.networking.TVshows.GenresItem?>?) {

       for (i in 0 until genre!!.size){

           genreMapTv[genre[i]!!.id!!] = genre[i]!!.name.toString()
       }
    }

    fun getGenreNameTv(genreId:Int): String? {

        return genreMapTv[genreId]
    }
}