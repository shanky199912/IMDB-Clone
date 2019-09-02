package com.example.imdbclone


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imdbclone.Database.AppDatabase
import com.example.imdbclone.Database.TvShows
import com.example.imdbclone.adapter.FavTvAdapter
import kotlinx.android.synthetic.main.fragment_fav_tv.*
import kotlinx.android.synthetic.main.fragment_fav_tv.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FavTvFragment : Fragment() {

    private val mFavShow: ArrayList<TvShows> = arrayListOf()
    private lateinit var mFavTvAdapter: FavTvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fav_tv, container, false)

        mFavTvAdapter = FavTvAdapter(context!!, mFavShow)
        view.rcv_fav_tv.layoutManager = GridLayoutManager(context!!, 3)
        view.rcv_fav_tv.adapter = mFavTvAdapter

        loadShow()

        return view
    }

    override fun onStart() {
        super.onStart()

        mFavTvAdapter.notifyDataSetChanged()
    }

    private fun loadShow() {
        val FavShow: List<TvShows> = AppDatabase.getDatabase(context!!)
            .tvshowDao().getFavTv()

       /* if (FavShow.isEmpty()) {
            view!!.layout_recycler_view_fav_tv_empty.visibility = View.VISIBLE
            return
        }*/

        FavShow.forEach {
            mFavShow.add(it)
        }
        mFavTvAdapter.notifyDataSetChanged()
    }


}
