package com.example.imdbclone


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imdbclone.Database.AppDatabase
import com.example.imdbclone.Database.Movie
import com.example.imdbclone.adapter.FavMovieAdapter
import com.example.imdbclone.adapter.SimilarMoviesAdapter
import com.example.imdbclone.networking.movies.ResultsItem
import kotlinx.android.synthetic.main.fragment_fav_movie.*
import kotlinx.android.synthetic.main.fragment_fav_movie.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FavMovieFragment : Fragment() {

    private val mFavMovies: ArrayList<Movie> = arrayListOf()
    private lateinit var mFavMovieAdapter: FavMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fav_movie, container, false)

        mFavMovieAdapter = FavMovieAdapter(context!!, mFavMovies)
        view.rcv_fav_movies.adapter = mFavMovieAdapter
        view.rcv_fav_movies.layoutManager = GridLayoutManager(context!!, 3) as RecyclerView.LayoutManager?

        loadFavMovies()


        return view
    }

    override fun onStart() {
        super.onStart()

        mFavMovieAdapter.notifyDataSetChanged()
    }

    private fun loadFavMovies() {

        val db = AppDatabase.getDatabase(context!!)

        val favMovie: List<Movie> = db.movieDao().getFavMovie()

        /*if (favMovie.isEmpty()) {
            view!!.layout_recycler_view_fav_movies_empty.visibility = View.VISIBLE
            return
        }*/

        favMovie.forEach {
            mFavMovies.add(it)
        }

        mFavMovieAdapter.notifyDataSetChanged()

    }


}
