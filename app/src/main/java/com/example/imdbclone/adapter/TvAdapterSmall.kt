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
import com.example.imdbclone.R
import com.example.imdbclone.Utils.Const
import com.example.imdbclone.networking.TVshows.ResultsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_tv_show.view.*
import kotlinx.android.synthetic.main.rcv_tv_large.view.*
import kotlinx.android.synthetic.main.rcv_tv_small.view.*

class TvAdapterSmall(private val context: Context, private val listTv: ArrayList<ResultsItem>) :
    RecyclerView.Adapter<TvHolderSmall>() {

    private lateinit var onBottomReachedListener: OnBottomReachedListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvHolderSmall {
        return TvHolderSmall(LayoutInflater.from(context).inflate(R.layout.rcv_tv_small, parent, false))
    }

    override fun getItemCount(): Int {
        return listTv.size//To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: TvHolderSmall, position: Int) {
        if (position == listTv.size - 1) {

            onBottomReachedListener.onBottomReached(position)
        }
        val tv = listTv[position]
        holder.bind(tv)//To change body of created functions use File | Settings | File Templates.
    }

    public fun setonBottomReachedListener(onBottomReachedListener: OnBottomReachedListener) {

        this.onBottomReachedListener = onBottomReachedListener
    }

}


class TvHolderSmall(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(tv: ResultsItem) {

        Picasso.get()
            .load(Const.img_url + tv.posterPath)
            .resize(400, 600)
            .onlyScaleDown()
            .into(itemView.img_tv_small)

        if (tv.name != null && tv.name.trim().isNotEmpty()) {
            itemView.text_tv_small.text = tv.name.trim()
        } else
            itemView.text_tv_small.text = ""

        itemView.setOnClickListener {

            val intent = Intent(itemView.context, TvDetailActivity::class.java)
            intent.putExtra("Tv_Id", tv.id)
            itemView.context.startActivity(intent)
        }

        itemView.img_heart_tv_small.setOnClickListener {

            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            AppDatabase.getDatabase(itemView.context!!).tvshowDao().addTvToFav(
                TvShows(
                    TvShowId = tv.id,
                    PosterPath = tv.posterPath,
                    name = tv.name!!.trim()
                )
            )
            Toast.makeText(itemView.context!!, "${tv.name.trim()} is added to Favourites", Toast.LENGTH_SHORT).show()
            itemView.img_heart_tv_small.setImageResource(R.drawable.baselinefav_sel)
        }

    }
}