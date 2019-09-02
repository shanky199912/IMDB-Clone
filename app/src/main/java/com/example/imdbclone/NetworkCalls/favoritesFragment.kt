package com.example.imdbclone.NetworkCalls


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.imdbclone.FavMovieFragment
import com.example.imdbclone.FavTvFragment
import com.example.imdbclone.R
import com.example.imdbclone.adapter.FavPagerAdapter
import kotlinx.android.synthetic.main.fragment_favorites.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class favoritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        val adapter = FavPagerAdapter(childFragmentManager, context!!)
        val favMovieFrag = FavMovieFragment()
        val favTvFrag = FavTvFragment()
        adapter.addFrag(favMovieFrag,"Movies")
        adapter.addFrag(favTvFrag,"Tv Shows")
        view.viewpager.adapter = adapter
        view.viewpagertab.setViewPager(view.viewpager)

        return view
    }


}
