package com.example.imdbclone.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imdbclone.R
import com.example.imdbclone.Utils.Const
import com.example.imdbclone.networking.TVshows.videos.ResultsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rcviewtrailer.view.*

class VideoTvAdapter(private val context: Context, private val listTv: ArrayList<ResultsItem>) :
    RecyclerView.Adapter<TvHolderVideos>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvHolderVideos {
        return TvHolderVideos(LayoutInflater.from(context).inflate(R.layout.rcviewtrailer, parent, false))
    }

    override fun getItemCount(): Int {
        return listTv.size//To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: TvHolderVideos, position: Int) {
        val tv = listTv[position]
        holder.bind(tv)//To change body of created functions use File | Settings | File Templates.
    }

}

class TvHolderVideos(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(tv: ResultsItem) {

        Picasso.get()
            .load(Const.image_base_url + tv.key + Const.image_base_url_thumbnail_hq)
            .fit()
            .centerCrop()
            .into(itemView.imageViewItem)

        itemView.imageViewItem.visibility = View.VISIBLE
        itemView.playBtn.visibility = View.VISIBLE

        itemView.crdviewTrailer.playBtn.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Const.Yotube_base_Url + tv.key))
            itemView.context.startActivity(intent)

        }

        if (tv.name != null && itemView.txtTrailerName.text.trim().isNotEmpty()) {
            itemView.txtTrailerName.text = tv.name.trim()
        } else
            itemView.txtTrailerName.text = ""

        itemView.txtTrailerName.visibility = View.VISIBLE

    }
}