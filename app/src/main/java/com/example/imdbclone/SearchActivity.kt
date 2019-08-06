package com.example.imdbclone

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imdbclone.adapter.SearchResultAdapter
import com.example.imdbclone.networking.Client.API_KEY
import com.example.imdbclone.networking.Client.retrofitCallBack
import com.example.imdbclone.networking.Client.service
import com.example.imdbclone.networking.Search.ResultsItem
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.content_search.*

@Suppress("UNCHECKED_CAST")
class SearchActivity : AppCompatActivity() {

    private var listSearch: ArrayList<ResultsItem> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(toolbar_search)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        handleIntent(intent)

    }

    override fun onNewIntent(intent: Intent?) {
        setIntent(intent)
        handleIntent(intent)
        super.onNewIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (Intent.ACTION_SEARCH == intent!!.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { searchQuery ->
                loadSearch(searchQuery)
                supportActionBar!!.title = searchQuery
            }
        }
    }

    private fun loadSearch(searchQuery: String) {

        service.listSearch(API_KEY, searchQuery).enqueue(retrofitCallBack { response, throwable ->

            response?.let {
                runOnUiThread {

                    listSearch = response.body()!!.results as ArrayList<ResultsItem>
                    val searchAdapter = SearchResultAdapter(this@SearchActivity, listSearch)
                    rcv_search.layoutManager = LinearLayoutManager(this@SearchActivity, RecyclerView.VERTICAL, false)
                    rcv_search.adapter = searchAdapter
                }
            }
            throwable?.let {

            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
