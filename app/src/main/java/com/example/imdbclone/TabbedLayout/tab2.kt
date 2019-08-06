package com.example.imdbclone.TabbedLayout


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.imdbclone.R
import com.example.imdbclone.adapter.MovieAdapter
import com.example.imdbclone.networking.Client
import com.example.imdbclone.networking.Client.API_KEY
import com.example.imdbclone.networking.Client.retrofitCallBack
import com.example.imdbclone.networking.Client.service
import com.example.imdbclone.networking.TmdbService
import com.example.imdbclone.networking.movies.ResultsItem
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


    private var mUpcoming = arrayListOf<ResultsItem?>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tab2, container, false)


        service.listUpcoming(API_KEY,1,"US").enqueue(retrofitCallBack{response, throwable ->

            response?.let {

                activity!!.runOnUiThread {

                    tab2_progress_bar.visibility = View.GONE
                    mUpcoming = response.body()?.results as ArrayList<ResultsItem?>

                    val mUpcomingAdapter = MovieAdapter(context!!, mUpcoming)

                    view.rcvUpcoming.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    view.rcvUpcoming.adapter = mUpcomingAdapter

                }

            }

            throwable?.let {

            }
        })

        return view
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
