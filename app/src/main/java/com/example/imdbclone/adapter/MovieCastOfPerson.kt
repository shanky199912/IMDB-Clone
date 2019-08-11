package com.example.imdbclone.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imdbclone.NetworkCalls.MovieDetail
import com.example.imdbclone.R
import com.example.imdbclone.Utils.Const
import com.example.imdbclone.networking.Person.CastItemMovie
import com.example.imdbclone.networking.Person.CastItemTv
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_cast.view.*

class MovieCastOfPerson(private val context: Context, private val listCastOfPersonMov: ArrayList<CastItemMovie>) :
    RecyclerView.Adapter<MovieCastViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCastViewHolder {

        return MovieCastViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_cast, parent, false))
    }

    override fun getItemCount(): Int {
        return listCastOfPersonMov.size
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: MovieCastViewHolder, position: Int) {
        val cast = listCastOfPersonMov[position]
        holder.bind(cast)//To change body of created functions use File | Settings | File Templates.
    }

}

class MovieCastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(castTv: CastItemMovie) {

        Picasso.get()
            .load(Const.img_url + castTv.posterPath)
            .fit()
            .centerCrop()
            .into(itemView.img_movie_cast)

        if (castTv.title != null && castTv.title.trim().isNotEmpty()) {
            itemView.text_movie_name.text = castTv.title.trim()
        } else
            itemView.text_movie_name.text = ""


        if (castTv.character != null && castTv.character.trim().isNotEmpty()) {
            itemView.text_movie_char.text = "as ${castTv.character.trim()}"
        } else
            itemView.text_movie_char.text = ""


        itemView.setOnClickListener {

            val intent = Intent(itemView.context, MovieDetail::class.java)
            intent.putExtra("MovieId", castTv.id)
            itemView.context.startActivity(intent)
        }
    }
}