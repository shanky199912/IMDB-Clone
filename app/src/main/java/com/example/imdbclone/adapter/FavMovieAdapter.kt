package com.example.imdbclone.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imdbclone.Database.AppDatabase
import com.example.imdbclone.Database.Movie
import com.example.imdbclone.NetworkCalls.MovieDetail
import com.example.imdbclone.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rcviewsimilarmovies.view.*
import kotlinx.android.synthetic.main.recyclerview.view.*

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

        if (AppDatabase.getDatabase(context).movieDao().isMovieFav(listMovie[position].id!!.toInt())){
            holder.itemView.imgHeart.setImageResource(R.drawable.baselinefav_sel)
            holder.itemView.imgHeart.isEnabled = false
        }
        else{
            holder.itemView.imgHeart.setImageResource(R.drawable.baselinefav_notsel)
            holder.itemView.imgHeart.isEnabled = true
        }
        //To change body of created functions use File | Settings | File Templates.
    }

}

class FavMovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun onBind(movie: Movie) {
        with(itemView) {

            Picasso.get().load("https://image.tmdb.org/t/p/original" + movie.PosterPath)
                .fit()
                .centerCrop()
                .into(imgSimilarMovie)

            if (movie.name != null && movie.name!!.trim().isNotEmpty()) {
                txtSimilarMovie.text = movie.name!!.trim()
            } else
                txtSimilarMovie.text = ""

            itemView.crdviewSimilarMovies.setOnClickListener {

                val i = Intent(itemView.context!!, MovieDetail::class.java)
                i.putExtra("MovieId", movie.id)
                itemView.context.startActivity(i)
            }

        }
    }
}