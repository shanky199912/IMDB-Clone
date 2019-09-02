package com.example.imdbclone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imdbclone.Database.Movie
import com.example.imdbclone.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rcviewsimilarmovies.view.*

class FavMovieAdapter(private val context: Context, private val listMovie: ArrayList<Movie>) :
    RecyclerView.Adapter<FavMovieHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavMovieHolder {
        return FavMovieHolder(
            LayoutInflater.from(context).inflate(
                R.layout.rcviewsimilarmovies,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listMovie.size //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: FavMovieHolder, position: Int) {

        val movie = listMovie[position]
        movie.let {
            holder.onBind(it)
        }
        //To change body of created functions use File | Settings | File Templates.
    }

}

class FavMovieHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
    fun onBind(movie: Movie) {
        with(itemView) {

            Picasso.get().load("https://image.tmdb.org/t/p/original"+movie.PosterPath)
                .fit()
                .centerCrop()
                .into(imgSimilarMovie)

            if (movie.name != null && movie.name!!.trim().isNotEmpty()) {
                txtSimilarMovie.text = movie.name!!.trim()
            } else
                txtSimilarMovie.text = ""
        }
    }
}