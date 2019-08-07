package com.example.imdbclone.TabbedLayout


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.imdbclone.R
import com.example.imdbclone.Utils.Const
import com.example.imdbclone.Utils.MovieGenre
import com.example.imdbclone.adapter.MovieAdapter
import com.example.imdbclone.networking.Client
import com.example.imdbclone.networking.Client.API_KEY
import com.example.imdbclone.networking.Client.retrofitCallBack
import com.example.imdbclone.networking.Client.service
import com.example.imdbclone.networking.TmdbService
import com.example.imdbclone.networking.movies.ResultsItem
import kotlinx.android.synthetic.main.fragment_tab1.*
import kotlinx.android.synthetic.main.fragment_tab1.view.*
import kotlinx.android.synthetic.main.fragment_tab1.view.rcvNowShowing
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class tab1 : Fragment() {

    private var pagesOver = false
    private var presentPage = 1
    private var loading = true
    private var previousTotal = 0
    private var visibleThreshold = 5

    private lateinit var mNowShowingAdapter: MovieAdapter

    private var mNowShowing = arrayListOf<ResultsItem?>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tab1, container, false)

        loadGenres()

        val mLayoutManager = LinearLayoutManager(context)
        view!!.rcvNowShowing.layoutManager = mLayoutManager as RecyclerView.LayoutManager?

        mNowShowingAdapter = MovieAdapter(context!!, mNowShowing)
        view.rcvNowShowing.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        view.rcvNowShowing.adapter = mNowShowingAdapter

        view.rcvNowShowing.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val visibleItemCount = view.rcvNowShowing.layoutManager!!.childCount
                val totalItemCount = view.rcvNowShowing.layoutManager!!.itemCount
                val firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition()

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false
                        previousTotal = totalItemCount
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {

                    loadMovies()
                    loading = true

                }
            }
        })

        loadMovies()
        return view
    }

    private fun loadMovies() {

        service.listNowShowing(API_KEY, presentPage, "US").enqueue(retrofitCallBack { response, throwable ->

            response?.let {

                activity?.runOnUiThread {

                    tab1_progress_bar.visibility = View.GONE
                    mNowShowing = response.body()!!.results!!

                    mNowShowingAdapter = MovieAdapter(context!!, mNowShowing)
                    view!!.rcvNowShowing.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    view!!.rcvNowShowing.adapter = mNowShowingAdapter

                    mNowShowingAdapter.notifyDataSetChanged();
                    if (response.body()!!.page == response.body()!!.totalPages)
                        pagesOver = true
                    else
                        presentPage++

                }

            }

            throwable?.let {

            }
        })

    }

    private fun loadGenres() {

        service.getMovieGenreList(API_KEY).enqueue(retrofitCallBack { response, throwable ->

            response?.let {

                activity!!.runOnUiThread {

                    tab1_progress_bar.visibility = View.GONE
                    MovieGenre().loadGenreList(response.body()!!.genres)
                    loadMovies()
                }
            }

            throwable?.let {

            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =

            tab1().apply {
                arguments = Bundle().apply {

                    this.putString("title", param1)
                }
            }

    }


}
