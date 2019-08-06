package com.example.imdbclone.NetworkCalls


import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imdbclone.R
import com.example.imdbclone.Utils.Const
import com.example.imdbclone.adapter.TvAdapterLarge
import com.example.imdbclone.adapter.TvAdapterSmall
import com.example.imdbclone.networking.Client.API_KEY
import com.example.imdbclone.networking.Client.retrofitCallBack
import com.example.imdbclone.networking.Client.service
import com.example.imdbclone.networking.TVshows.ResultsItem
import kotlinx.android.synthetic.main.fragment_tv_show.*
import kotlinx.android.synthetic.main.fragment_tv_show.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
@Suppress("UNCHECKED_CAST")
class tvShowFragment : Fragment() {

    private val presentPage: Int = 1
    private var listTv: ArrayList<ResultsItem> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tv_show, container, false)

        view.layout_tv.visibility = View.GONE

        val connectivityManager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        val isConnected = activeNetworkInfo?.isConnected == true

        if (isConnected) {
            loadTvShowsAiringToday()
            loadTvShowOnTheAir()
            loadTvShowPopular()
            loadTvShowTopRated()
        }

        view.tv_Text_ViewAll_1.setOnClickListener {

            val intent = Intent(context, ViewAllActivity::class.java)
            intent.putExtra("Id", Const.View_All_airingToday)
            context!!.startActivity(intent)
        }


        view.tv_Text_ViewAll_3.setOnClickListener {

            val intent = Intent(context, ViewAllActivity::class.java)
            intent.putExtra("Id", Const.View_All_Popular)
            context!!.startActivity(intent)
        }

        view.tv_Text_ViewAll_2.setOnClickListener {

            val intent = Intent(context, ViewAllActivity::class.java)
            intent.putExtra("Id", Const.View_All_onTheAir)
            context!!.startActivity(intent)
        }

        view.tv_Text_ViewAll_4.setOnClickListener {

            val intent = Intent(context, ViewAllActivity::class.java)
            intent.putExtra("Id", Const.View_All_TopRated)
            context!!.startActivity(intent)
        }

        return view
    }

    private fun loadTvShowsAiringToday() {

        service.listAiringToday(API_KEY, presentPage).enqueue(retrofitCallBack { response, throwable ->

            response?.let {
                activity!!.runOnUiThread {

                    tv_progress_bar.visibility = View.GONE
                    listTv = response.body()!!.results as ArrayList<ResultsItem>
                    val airingTodayAdapter = TvAdapterLarge(context!!, listTv)
                    rcv_tv_airingToday.layoutManager =
                        LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
                    rcv_tv_airingToday.adapter = airingTodayAdapter
                    setVisibility()
                }
            }
            throwable?.let {

            }
        })
    }

    private fun loadTvShowOnTheAir() {

        service.listOnTheAir(API_KEY, presentPage).enqueue(retrofitCallBack { response, throwable ->

            response?.let {
                activity!!.runOnUiThread {
                    tv_progress_bar.visibility = View.GONE
                    listTv = response.body()!!.results as ArrayList<ResultsItem>
                    val onTheAirAdapter = TvAdapterSmall(context!!, listTv)
                    rcv_tv_OntheAir.layoutManager =
                        LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
                    rcv_tv_OntheAir.adapter = onTheAirAdapter
                    setVisibility()
                }
            }

            throwable?.let {

            }
        })
    }

    private fun loadTvShowPopular() {

        service.listPopular(API_KEY, presentPage).enqueue(retrofitCallBack { response, throwable ->

            response?.let {
                activity!!.runOnUiThread {

                    tv_progress_bar.visibility = View.GONE
                    listTv = response.body()!!.results as ArrayList<ResultsItem>
                    val popularAdapter = TvAdapterLarge(context!!, listTv)
                    rcv_tv_Popular.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
                    rcv_tv_Popular.adapter = popularAdapter
                    setVisibility()
                }
            }
            throwable?.let {

            }
        })
    }

    private fun loadTvShowTopRated() {

        service.listTopRated(API_KEY, presentPage).enqueue(retrofitCallBack { response, throwable ->
            response?.let {
                activity!!.runOnUiThread {

                    tv_progress_bar.visibility = View.GONE
                    listTv = response.body()!!.results as ArrayList<ResultsItem>
                    val topRatedAdapter = TvAdapterSmall(context!!, listTv)
                    rcv_tv_Toprated.layoutManager =
                        LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
                    rcv_tv_Toprated.adapter = topRatedAdapter
                    setVisibility()
                }
            }
            throwable?.let {

            }
        })
    }

    private fun setVisibility() {
        layout_tv.visibility = View.VISIBLE
    }


}
