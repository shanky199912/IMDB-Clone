package com.example.imdbclone.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imdbclone.NetworkCalls.MovieDetail
import com.example.imdbclone.R
import com.example.imdbclone.Utils.MovieGenre
import com.example.imdbclone.networking.movies.GenresItem
import com.example.imdbclone.networking.movies.ResultsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recyclerview.view.*
import kotlinx.android.synthetic.main.recyclerview.view.imgmovie

class MovieAdapter(val context: Context, private val listMovie: ArrayList<ResultsItem?>) :
    RecyclerView.Adapter<MovieViewHolder>() {

    private val listGenre: ArrayList<GenresItem?>? = arrayListOf()
    private val mapGenre: HashMap<Int,String> = hashMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        return MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview, parent, false))
    }

    override fun getItemCount(): Int {
        return listMovie.size
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        val movie = listMovie[position]
        holder.bind(movie)



        setGenres(holder, listMovie[position])

        //To change body of created functions use File | Settings | File Templates.
    }

    private fun setGenres(holder: MovieViewHolder, resultsItem: ResultsItem?) {

        var genreStr = ""
        for (i in 0 until resultsItem!!.genreIds!!.size) {

            genreStr += MovieGenre().getGenreName(resultsItem.genreIds!![i]!!) + " ,"


        }
        if (genreStr.isNotEmpty()) {
            holder.itemView.txtGenre.text = (genreStr.substring(0, genreStr.length-2))
        } else
            holder.itemView.txtGenre.text = ""
    }


}


class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(movie: ResultsItem?) {

        with(itemView) {

            Picasso.get()
                .load("https://image.tmdb.org/t/p/original" + movie!!.posterPath)
                .fit()
                .centerCrop()
                .into(itemView.imgmovie)

            if (movie.title != null) {

                itemView.txtmovname.text = movie.originalTitle
            } else
                itemView.txtmovname.text = ""

            if (movie.voteAverage != null && movie.voteAverage > 0) {
                itemView.txtRat.text = movie.voteAverage.toString()
            } else {
                itemView.txtRat.visibility = View.GONE
                itemView.imgStar.visibility = View.GONE
            }

            itemView.imgHeart.visibility = View.VISIBLE


            itemView.setOnClickListener {

                val intent = Intent(itemView.context, MovieDetail::class.java)
                intent.putExtra("MovieId", movie.id)
                itemView.context.startActivity(intent)
            }

        }


    }


}