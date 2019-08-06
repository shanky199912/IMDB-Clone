package com.example.imdbclone.NetworkCalls

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imdbclone.R
import com.example.imdbclone.Utils.Const
import com.example.imdbclone.adapter.TvAdapterSmall
import com.example.imdbclone.networking.Client.API_KEY
import com.example.imdbclone.networking.Client.retrofitCallBack
import com.example.imdbclone.networking.Client.service
import com.example.imdbclone.networking.TVshows.*
import kotlinx.android.synthetic.main.activity_person_detail.*
import kotlinx.android.synthetic.main.activity_view_all.*
import retrofit2.Call
import retrofit2.Callback

class ViewAllActivity : AppCompatActivity() {

    private val mTvShow: ArrayList<ResultsItem> = arrayListOf()
    private lateinit var mTvShowAdapter: TvAdapterSmall

    private var pagesOver = false
    private var presentPage = 1
    private var loading = true
    private var previousTotal = 0
    private var visibleThreshold = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all)
        setSupportActionBar(toolbarViewAll)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val mTvShowType = intent.getIntExtra("Id", -1)
        if (mTvShowType == -1) finish()

        when (mTvShowType) {

            Const.View_All_airingToday -> supportActionBar?.title = "Airing Today Tv Shows"

            Const.View_All_onTheAir -> supportActionBar?.title = "On the Air Tv Shows"

            Const.View_All_Popular -> supportActionBar?.title = "Popular Tv Shows"

            Const.View_All_TopRated -> supportActionBar?.title = "Top Rated Tv Shows"
        }

        val mLayoutManager = GridLayoutManager(this@ViewAllActivity, 3)
        rcv_View_All.layoutManager = mLayoutManager
        mTvShowAdapter = TvAdapterSmall(this@ViewAllActivity, mTvShow)
        rcv_View_All.layoutManager = GridLayoutManager(this@ViewAllActivity, 3)
        rcv_View_All.adapter = mTvShowAdapter
        rcv_View_All.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = mLayoutManager.childCount
                val totalItemCount = mLayoutManager.itemCount
                val firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    loadTvShow(mTvShowType)
                    loading = true;
                }

            }

        })

        loadTvShow(mTvShowType)

    }

    override fun onStart() {
        super.onStart()

        mTvShowAdapter.notifyDataSetChanged()
    }


    private fun loadTvShow(mTvShowType: Int) {
        if (pagesOver) return

        when (mTvShowType) {

            Const.View_All_airingToday ->
                service.listAiringToday(API_KEY, presentPage).enqueue(retrofitCallBack { response, throwable ->

                    response?.let {
                        runOnUiThread {
                            if (!response.isSuccessful) {
                            }

                            tv_progress_bar_viewAll.visibility = View.GONE
                            for (tvShowBrief in response.body()!!.results!!) {
                                mTvShow.add(tvShowBrief!!)
                            }
                            mTvShowAdapter.notifyDataSetChanged()

                            if (response.body()!!.page == response.body()!!.totalPages) {
                                pagesOver = true
                            } else
                                presentPage++
                        }
                    }
                    throwable?.let {

                    }
                })

            Const.View_All_onTheAir ->
                service.listOnTheAir(API_KEY, presentPage).enqueue(retrofitCallBack { response, throwable ->

                    response?.let {
                        runOnUiThread {

                            tv_progress_bar_viewAll.visibility = View.GONE
                            for (tvShowBrief in response.body()!!.results!!) {
                                mTvShow.add(tvShowBrief!!)
                            }
                            mTvShowAdapter.notifyDataSetChanged()

                            if (response.body()!!.page == response.body()!!.totalPages) {
                                pagesOver = true
                            } else
                                presentPage++
                        }
                    }
                    throwable?.let {

                    }
                })

            Const.View_All_Popular ->
                service.listPopular(API_KEY, presentPage).enqueue(retrofitCallBack { response, throwable ->

                    response?.let {
                        runOnUiThread {

                            tv_progress_bar_viewAll.visibility = View.GONE
                            for (tvShowBrief in response.body()!!.results!!) {
                                mTvShow.add(tvShowBrief!!)
                            }
                            mTvShowAdapter.notifyDataSetChanged()

                            if (response.body()!!.page == response.body()!!.totalPages) {
                                pagesOver = true
                            } else
                                presentPage++
                        }
                    }
                    throwable?.let {

                    }
                })

            Const.View_All_TopRated ->
                service.listTopRated(API_KEY, presentPage).enqueue(retrofitCallBack { response, throwable ->

                    response?.let {
                        runOnUiThread {
                            tv_progress_bar_viewAll.visibility = View.GONE
                            for (tvShowBrief in response.body()!!.results!!) {
                                mTvShow.add(tvShowBrief!!)
                            }
                            mTvShowAdapter.notifyDataSetChanged()

                            if (response.body()!!.page == response.body()!!.totalPages) {
                                pagesOver = true
                            } else
                                presentPage++
                        }
                    }
                    throwable?.let {

                    }
                })
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
