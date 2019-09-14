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
import com.example.imdbclone.Database.TvShows
import com.example.imdbclone.NetworkCalls.OnBottomReachedListener
import com.example.imdbclone.NetworkCalls.TvDetailActivity
import com.example.imdbclone.NetworkCalls.ViewAllActivity
import com.example.imdbclone.NetworkCalls.tvShowFragment
import com.example.imdbclone.R
import com.example.imdbclone.Utils.Const
import com.example.imdbclone.Utils.MovieGenre
import com.example.imdbclone.Utils.TvGenre
import com.example.imdbclone.networking.TVshows.ResultsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_tv_show.view.*
import kotlinx.android.synthetic.main.rcv_tv_large.view.*

class TvAdapterLarge(private val context: Context, private val listTv: ArrayList<ResultsItem>) :
    RecyclerView.Adapter<TvHolderLarge>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvHolderLarge {
        return TvHolderLarge(
            LayoutInflater.from(context).inflate(
                R.layout.rcv_tv_large,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listTv.size//To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: TvHolderLarge, position: Int) {
        val tv = listTv[position]
        holder.bind(tv)//To change body of created functions use File | Settings | File Templates.


        setGenres(holder, listTv[position])
    }


    private fun setGenres(holder: TvHolderLarge, resultsItem: ResultsItem) {

        var genreStr = ""
        for (element in resultsItem.genreIds!!) {

            if (element == null) continue
            if (TvGenre().getGenreNameTv(element) == null) continue
            genreStr += TvGenre().getGenreNameTv(element) + " ,"


        }
        if (genreStr.isNotEmpty()) {
            holder.itemView.text_tv_genre_large.text = (genreStr.substring(0, genreStr.length - 2))
        } else
            holder.itemView.text_tv_genre_large.text = ""
    }

}

class TvHolderLarge(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(tv: ResultsItem) {

        Picasso.get()
            .load(Const.img_url + tv.backdropPath)
            .fit()
            .centerCrop()
            .into(itemView.img_tv_large)

        itemView.text_tv_large.visibility = View.VISIBLE

        if (tv.name != null && tv.name.trim().isNotEmpty()) {
            itemView.text_tv_large.text = tv.name.trim()
        } else {
            itemView.text_tv_large.text = ""
        }

        if (tv.voteAverage != null) {
            itemView.vote_tv_text.text = tv.voteAverage.toString()
            itemView.vote_tv_star.visibility = View.VISIBLE
        } else
            itemView.vote_tv_text.text = ""

        itemView.setOnClickListener {

            val intent = Intent(itemView.context, TvDetailActivity::class.java)
            intent.putExtra("Tv_Id", tv.id)
            itemView.context.startActivity(intent)
        }

        itemView.img_heart_tv_large.setOnClickListener {

            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            AppDatabase.getDatabase(itemView.context!!).tvshowDao().addTvToFav(
                TvShows(
                    TvShowId = tv.id,
                    PosterPath = tv.posterPath,
                    name = tv.name!!.trim()
                )
            )
            Toast.makeText(
                itemView.context!!,
                "${tv.name.trim()} is added to Favourites",
                Toast.LENGTH_SHORT
            ).show()
            itemView.img_heart_tv_large.setImageResource(R.drawable.baselinefav_sel)
        }

    }
}