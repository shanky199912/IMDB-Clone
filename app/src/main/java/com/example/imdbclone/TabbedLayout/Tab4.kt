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
import com.example.imdbclone.networking.movies.ResultsItem
import kotlinx.android.synthetic.main.fragment_tab4.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Tab4 : Fragment() {

    private var mPopular = arrayListOf<ResultsItem?>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tab4, container, false)


        service.listPopular(API_KEY, 1, "US").enqueue(retrofitCallBack { response, throwable ->

            response?.let {

                activity?.runOnUiThread {

                    tab4_progress_bar.visibility = View.GONE
                    mPopular = response.body()?.results as ArrayList<ResultsItem?>

                    val mPopularAdapter = MovieAdapter(context!!, mPopular)
                    rcvPopular.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                    rcvPopular.adapter = mPopularAdapter
                }

            }

            throwable?.let {

            }
        })

        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =

            Tab4().apply {
                arguments = Bundle().apply {

                    this.putString("title", param1)
                }
            }

    }

}
