package com.example.imdbclone.adapter

import android.content.Context
import android.content.Intent
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.imdbclone.Database.AppDatabase
import com.example.imdbclone.Database.Movie
import com.example.imdbclone.NetworkCalls.MovieDetail
import com.example.imdbclone.R
import com.example.imdbclone.networking.movies.ResultsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rcviewsimilarmovies.view.*

class SimilarMoviesAdapter(private val context: Context, private val listMovie: ArrayList<ResultsItem?>?) :
    RecyclerView.Adapter<SimilarMovieHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMovieHolder {
        return SimilarMovieHolder(LayoutInflater.from(context).inflate(R.layout.rcviewsimilarmovies, parent, false))
    }

    override fun getItemCount(): Int {
        return listMovie!!.size //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: SimilarMovieHolder, position: Int) {

        val movie = listMovie?.get(position)
        movie?.let { holder.bind(it) }

        if (AppDatabase.getDatabase(context).movieDao().isMovieFav(this.listMovie!![position]!!.id!!)){
            holder.itemView.imgheartSimilarMov.setImageResource(R.drawable.baselinefav_sel)
            holder.itemView.imgheartSimilarMov.isEnabled = false
        }
        else{
            holder.itemView.imgheartSimilarMov.setImageResource(R.drawable.baselinefav_notsel)
            holder.itemView.imgheartSimilarMov.isEnabled = true
        }
        //To change body of created functions use File | Settings | File Templates.
    }

}

class SimilarMovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(movie: ResultsItem) {

        itemView.imgSimilarMovie.visibility = View.GONE
        itemView.txtSimilarMovie.visibility = View.GONE
        itemView.imgheartSimilarMov.visibility = View.GONE
        Picasso.get()
            .load("https://image.tmdb.org/t/p/original" + movie.posterPath)
            .fit()
            .centerCrop()
            .into(itemView.imgSimilarMovie)

        if (movie.title != null && movie.title.trim().isNotEmpty()) {

            itemView.txtSimilarMovie.text = movie.title.trim()
        } else {
            itemView.txtSimilarMovie.text = ""

        }

        itemView.imgSimilarMovie.visibility = View.VISIBLE
        itemView.txtSimilarMovie.visibility = View.VISIBLE
        itemView.imgheartSimilarMov.visibility = View.VISIBLE

        itemView.crdviewSimilarMovies.setOnClickListener {

            val intent = Intent(itemView.context, MovieDetail::class.java)
            intent.putExtra("MovieId", movie.id)
            itemView.context.startActivity(intent)
        }

        itemView.imgheartSimilarMov.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            AppDatabase.getDatabase(itemView.context!!).movieDao().addMovToFav(
                Movie(
                    MovieId = movie.id,
                    PosterPath = movie.posterPath,
                    name = movie.title!!.trim()
                )
            )
            Toast.makeText(itemView.context!!, "${movie.title.trim()} is added to Favourites", Toast.LENGTH_SHORT).show()
            itemView.imgheartSimilarMov.setImageResource(R.drawable.baselinefav_sel)
            itemView.imgheartSimilarMov.isEnabled = false
        }
    }
}