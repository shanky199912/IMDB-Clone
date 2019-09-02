package com.example.imdbclone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imdbclone.Database.TvShows
import com.example.imdbclone.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rcviewsimilarmovies.view.*

class FavTvAdapter(private val context:Context, private val listFavTv:ArrayList<TvShows>):RecyclerView.Adapter<FavTvHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavTvHolder {
        return FavTvHolder(LayoutInflater.from(context).inflate(R.layout.rcviewsimilarmovies,parent,false))
    }

    override fun getItemCount(): Int {
        return listFavTv.size //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: FavTvHolder, position: Int) {
      val tv = listFavTv[position]
        tv.let { holder.onBind(it) }

       //To change body of created functions use File | Settings | File Templates.
    }

}

class FavTvHolder(itemView:View):RecyclerView.ViewHolder(itemView){

    fun onBind(tv: TvShows){
        with(itemView){

            Picasso.get().load("https://image.tmdb.org/t/p/original"+tv.PosterPath)
                .fit()
                .centerCrop()
                .into(itemView.imgSimilarMovie)


            if (tv.name!=null && tv.name.trim().isNotEmpty()) {
                txtSimilarMovie.text = tv.name.trim()
            }
            else
                txtSimilarMovie.text = ""
        }
    }

}