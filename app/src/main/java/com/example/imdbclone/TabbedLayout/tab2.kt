package com.example.imdbclone.TabbedLayout


import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imdbclone.BroadCast.ConnectivityBroadcastReciever
import com.example.imdbclone.NetworkCalls.OnBottomReachedListener

import com.example.imdbclone.R
import com.example.imdbclone.adapter.MovieAdapter
import com.example.imdbclone.networking.Client
import com.example.imdbclone.networking.Client.API_KEY
import com.example.imdbclone.networking.Client.retrofitCallBack
import com.example.imdbclone.networking.Client.service
import com.example.imdbclone.networking.TmdbService
import com.example.imdbclone.networking.movies.ResultsItem
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_tab2.*
import kotlinx.android.synthetic.main.fragment_tab2.view.*
import kotlinx.android.synthetic.main.fragment_tab2.view.tab2_progress_bar
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
class tab2 : Fragment() {

    private var pagesOver = false
    private var presentPage = 1
    private var loading = true
    private var previousTotal = 0
    private var visibleThreshold = 5
    private var isFragmentLoaded: Boolean = false
    private var isBroadcastRecieverRegistered = false

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var activeNetworkInfo: NetworkInfo
    private var isConnected: Boolean = false
    private lateinit var mUpcomingAdapter: MovieAdapter
    private lateinit var mConnectivitySnackbar: Snackbar
    private lateinit var mConnectivityBroadcastReciever: ConnectivityBroadcastReciever

    private var mUpcoming = arrayListOf<ResultsItem?>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tab2, container, false)

        val mLayoutManager = LinearLayoutManager(context)
        view!!.rcvUpcoming.layoutManager = mLayoutManager as RecyclerView.LayoutManager?

        mUpcomingAdapter = MovieAdapter(context!!, mUpcoming)
        view.rcvUpcoming.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        view.rcvUpcoming.adapter = mUpcomingAdapter

        mUpcomingAdapter.setonBottomReachedListener(object : OnBottomReachedListener {
            override fun onBottomReached(position: Int) {

                if (pagesOver) {
                    return
                } else {
                    presentPage++
                    loadMovies()

                }
            }

        })


        return view
    }

    override fun onStart() {
        super.onStart()

        mUpcomingAdapter.notifyDataSetChanged()
    }

    private fun loadMovies() {
        if (pagesOver) {
            return
        }

        service.listUpcoming(API_KEY, presentPage, "US").enqueue(retrofitCallBack { response, throwable ->

            response?.let {

                activity!!.runOnUiThread {

                    tab2_progress_bar.visibility = View.GONE

                    for (movie in response.body()!!.results!!) {
                        mUpcoming.add(movie)
                    }
                    mUpcomingAdapter.notifyDataSetChanged()


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =

            tab2().apply {
                arguments = Bundle().apply {

                    this.putString("title", param1)
                }
            }

    }


}
