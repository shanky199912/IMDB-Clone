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
import com.example.imdbclone.NetworkCalls.OnBottomReachedListener
import com.example.imdbclone.R
import com.example.imdbclone.Utils.MovieGenre
import com.example.imdbclone.networking.movies.ResultsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview.view.*
import kotlinx.android.synthetic.main.recyclerview.view.imgmovie

class MovieAdapter(val context: Context, private val listMovie: ArrayList<ResultsItem?>) :
    RecyclerView.Adapter<MovieViewHolder>() {

    private lateinit var onBottomReachedListener: OnBottomReachedListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        return MovieViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.recyclerview,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listMovie.size
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        if (position == listMovie.size - 1) {

            onBottomReachedListener.onBottomReached(position)
        }

        setGenres(holder, listMovie[position])

        val movie = listMovie[position]
        holder.bind(movie)

        if (AppDatabase.getDatabase(context).movieDao().isMovieFav(listMovie[position]!!.id!!)){
            holder.itemView.imgHeart.setImageResource(R.drawable.baselinefav_sel)
            holder.itemView.imgHeart.isEnabled = false
        }
        else{
            holder.itemView.imgHeart.setImageResource(R.drawable.baselinefav_notsel)
            holder.itemView.imgHeart.isEnabled = true
        }


        //To change body of created functions use File | Settings | File Templates.
    }

     fun setonBottomReachedListener(onBottomReachedListener: OnBottomReachedListener) {

        this.onBottomReachedListener = onBottomReachedListener
    }

    private fun setGenres(holder: MovieViewHolder, resultsItem: ResultsItem?) {

        var genreStr = ""
        for (element in resultsItem!!.genreIds!!) {

            if(element == null) continue
            if (MovieGenre().getGenreName(element) == null) continue
            genreStr += MovieGenre().getGenreName(element) + " ,"

        }
        if (genreStr.isNotEmpty()) {
            holder.itemView.txtGenre.text = (genreStr.substring(0, genreStr.length - 2))
        } else
            holder.itemView.txtGenre.text = ""
    }


}


class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(movie: ResultsItem?) {

        with(itemView) {

            Picasso.get()
                .load("https://image.tmdb.org/t/p/original" + movie!!.posterPath)
                .resize(400, 500)
                .onlyScaleDown()
                .into(itemView.imgmovie)

            if (movie.title != null) {

                itemView.txtmovname.text = movie.title.trim()
            } else
                itemView.txtmovname.text = ""

            if (itemView.txtRat.text != null && movie.voteAverage!! > 0) {
                itemView.txtRat.text = movie.voteAverage.toString()
                itemView.imgStar.visibility = View.VISIBLE
            } else {
                itemView.txtRat.text = ""
                itemView.imgStar.visibility = View.GONE
            }

            itemView.imgHeart.visibility = View.VISIBLE


            itemView.setOnClickListener {

                val intent = Intent(itemView.context, MovieDetail::class.java)
                intent.putExtra("MovieId", movie.id)
                itemView.context.startActivity(intent)
            }


            itemView.imgHeart.setOnClickListener {
                it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                AppDatabase.getDatabase(context!!).movieDao().addMovToFav(
                    Movie(
                        MovieId = movie.id,
                        PosterPath = movie.posterPath,
                        name = movie.title
                    )
                )
                Toast.makeText(
                    context!!,
                    "${movie.title!!.trim()} is added to Favourites",
                    Toast.LENGTH_SHORT
                ).show()
                itemView.imgHeart.setImageResource(R.drawable.baselinefav_sel)
                itemView.imgHeart.isEnabled = false
            }


        }


    }


}