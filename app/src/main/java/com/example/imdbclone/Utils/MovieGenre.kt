package com.example.imdbclone.Utils

import com.example.imdbclone.networking.movies.GenresItem

class MovieGenre {

    private val genreMap: HashMap<Int, String> = hashMapOf()
    fun isGenreListLoaded(): Boolean {
        return (genreMap!=null)
    }

    fun loadGenreList(genre: List<GenresItem?>?) {

        if (genre == null) {
            return
        }

        genre.forEach {
            genreMap[it!!.id!!] = it.name!!.toString()
        }

    }

    fun getGenreName(genreId: Int): String? {

        return genreMap[genreId]
    }
}