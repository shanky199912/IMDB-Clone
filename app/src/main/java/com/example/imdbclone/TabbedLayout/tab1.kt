package com.example.imdbclone.TabbedLayout


import android.content.Context
import android.content.IntentFilter
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
import com.example.imdbclone.BroadCast.ConnectivityRecieverListener

import com.example.imdbclone.R
import com.example.imdbclone.Utils.MovieGenre
import com.example.imdbclone.adapter.MovieAdapter
import com.example.imdbclone.networking.Client.API_KEY
import com.example.imdbclone.networking.Client.retrofitCallBack
import com.example.imdbclone.networking.Client.service
import com.example.imdbclone.networking.movies.ResultsItem
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_tab1.*
import kotlinx.android.synthetic.main.fragment_tab1.view.rcvNowShowing

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
    private var isFragmentLoaded: Boolean = false
    private var isBroadcastRecieverRegistered = false

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var activeNetworkInfo: NetworkInfo
    private var isConnected: Boolean = false
    private lateinit var mNowShowingAdapter: MovieAdapter
    private lateinit var mConnectivitySnackbar: Snackbar
    private lateinit var mConnectivityBroadcastReciever: ConnectivityBroadcastReciever

    private var mNowShowing = arrayListOf<ResultsItem?>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tab1, container, false)

        val mLayoutManager = LinearLayoutManager(context)
        view!!.rcvNowShowing.layoutManager = mLayoutManager as RecyclerView.LayoutManager?

        mNowShowingAdapter = MovieAdapter(context!!, mNowShowing)
        view.rcvNowShowing.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        view.rcvNowShowing.adapter = mNowShowingAdapter

        /*view.rcvNowShowing.addOnScrollListener(object : RecyclerView.OnScrollListener() {

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
        })*/


        if (isNetworkAvailable()) {
            isFragmentLoaded = true
            loadFragment()
        }

        return view
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = activity!!.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    override fun onStart() {
        super.onStart()

        mNowShowingAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()

        if (!isFragmentLoaded && !isConnected) {

            mConnectivitySnackbar = Snackbar.make(
                activity!!.findViewById(R.id.container),
                "No Network Connection",
                Snackbar.LENGTH_INDEFINITE
            )
            mConnectivitySnackbar.show()
            mConnectivityBroadcastReciever = ConnectivityBroadcastReciever(object : ConnectivityRecieverListener {
                override fun onNetworkConnectionConnected() {

                    mConnectivitySnackbar.dismiss()
                    isFragmentLoaded = true
                    loadFragment()
                    isBroadcastRecieverRegistered = false
                    activity!!.unregisterReceiver(mConnectivityBroadcastReciever)
                }

            })

            val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
            isBroadcastRecieverRegistered = true
            activity!!.registerReceiver(mConnectivityBroadcastReciever, intentFilter)

        } else if (!isFragmentLoaded && isConnected) {
            isFragmentLoaded = true
            loadFragment()
        }
    }

    override fun onPause() {
        super.onPause()

        if (isBroadcastRecieverRegistered) {

            mConnectivitySnackbar.dismiss()
            isBroadcastRecieverRegistered = false
            activity!!.unregisterReceiver(mConnectivityBroadcastReciever)
        }
    }

    private fun loadFragment() {

        if (MovieGenre().isGenreListLoaded()) {
            loadMovies()
        } else {
            loadGenres()
        }
    }

    private fun loadGenres() {

        service.getMovieGenreList(API_KEY,"en-US").enqueue(retrofitCallBack { response, throwable ->

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


    private fun loadMovies() {

        if (pagesOver) {
            return
        }

        service.listNowShowing(API_KEY, presentPage, "US").enqueue(retrofitCallBack { response, throwable ->

            response?.let {

                activity?.runOnUiThread {

                    tab1_progress_bar.visibility = View.GONE
                    val mNowShowing = response.body()!!.results!!

                    val mNowShowingAdapter = MovieAdapter(context!!, mNowShowing)
                    view!!.rcvNowShowing.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    view!!.rcvNowShowing.adapter = mNowShowingAdapter
                    /* for (movie in response.body()!!.results!!) {
                         mNowShowing.add(movie)
                     }*/
                    mNowShowingAdapter.notifyDataSetChanged()


                    /* if (response.body()!!.page == response.body()!!.totalPages)
                         pagesOver = true
                     else
                         presentPage++*/

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
