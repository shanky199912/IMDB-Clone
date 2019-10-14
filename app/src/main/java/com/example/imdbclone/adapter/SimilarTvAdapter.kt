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
import com.example.imdbclone.NetworkCalls.TvDetailActivity
import com.example.imdbclone.R
import com.example.imdbclone.Utils.Const
import com.example.imdbclone.networking.TVshows.ResultsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rcviewsimilarmovies.view.*

class SimilarTvAdapter(private val context: Context, private val listTv: ArrayList<ResultsItem>) :
    RecyclerView.Adapter<TvHolderSimilar>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvHolderSimilar {
        return TvHolderSimilar(LayoutInflater.from(context).inflate(R.layout.rcviewsimilarmovies, parent, false))
    }

    override fun getItemCount(): Int {
        return listTv.size//To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: TvHolderSimilar, position: Int) {
        val tv = listTv[position]
        holder.bind(tv)
        if (AppDatabase.getDatabase(context).tvshowDao().isShowFav(listTv[position].id!!.toInt())) {
            holder.itemView.imgheartSimilarMov.setImageResource(R.drawable.baselinefav_sel)
            holder.itemView.imgheartSimilarMov.isEnabled = false
        } else {
            holder.itemView.imgheartSimilarMov.setImageResource(R.drawable.baselinefav_notsel)
            holder.itemView.imgheartSimilarMov.isEnabled = true
        }//To change body of created functions use File | Settings | File Templates.
    }

}

class TvHolderSimilar(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(tv: ResultsItem) {

        Picasso.get()
            .load(Const.img_url + tv.posterPath)
            .fit()
            .centerCrop()
            .into(itemView.imgSimilarMovie)

        if (tv.name != null) itemView.txtSimilarMovie.text = tv.name.trim()
        else
            itemView.txtSimilarMovie.text = ""



        itemView.setOnClickListener {
            val intent = Intent(itemView.context, TvDetailActivity::class.java)
            intent.putExtra("Tv_Id", tv.id)
            itemView.context.startActivity(intent)
        }

        itemView.imgheartSimilarMov.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            AppDatabase.getDatabase(itemView.context!!).tvshowDao().addTvToFav(
                TvShows(
                    TvShowId = tv.id,
                    PosterPath = tv.posterPath,
                    name = tv.name!!.trim()
                )
            )
            Toast.makeText(itemView.context!!, "${tv.name.trim()} is added to Favourites", Toast.LENGTH_SHORT).show()
            itemView.imgheartSimilarMov.setImageResource(R.drawable.baselinefav_sel)
            itemView.imgheartSimilarMov.isEnabled = false
        }
    }
}