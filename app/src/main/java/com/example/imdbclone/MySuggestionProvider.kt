package com.example.imdbclone

import android.content.SearchRecentSuggestionsProvider

class MySuggestionProvider() : SearchRecentSuggestionsProvider(){

    init {
        setupSuggestions(AUTHORITY,MODE)
    }

    companion object{

        const val AUTHORITY = "com.example.imdbclone.MySuggestionProvider"
        const val MODE =SearchRecentSuggestionsProvider.DATABASE_MODE_QUERIES
    }

}