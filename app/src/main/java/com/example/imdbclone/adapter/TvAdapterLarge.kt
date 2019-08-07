package com.example.imdbclone.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imdbclone.NetworkCalls.TvDetailActivity
import com.example.imdbclone.NetworkCalls.ViewAllActivity
import com.example.imdbclone.R
import com.example.imdbclone.Utils.Const
import com.example.imdbclone.networking.TVshows.ResultsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_tv_show.view.*
import kotlinx.android.synthetic.main.rcv_tv_large.view.*

class TvAdapterLarge(private val context: Context, private val listTv: ArrayList<ResultsItem>) :
    RecyclerView.Adapter<TvHolderLarge>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvHolderLarge {
        return TvHolderLarge(LayoutInflater.from(context).inflate(R.layout.rcv_tv_large, parent, false))
    }

    override fun getItemCount(): Int {
        return listTv.size//To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: TvHolderLarge, position: Int) {
        val tv = listTv[position]
        holder.bind(tv)//To change body of created functions use File | Settings | File Templates.
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

        if (itemView.text_tv_large.text != null) {
            itemView.text_tv_large.text = tv.name!!.trimEnd()
        } else {
            itemView.text_tv_large.text = ""
        }

        if (itemView.vote_tv_text.text != null) {
            itemView.vote_tv_text.text = tv.voteAverage.toString()
            itemView.vote_tv_star.visibility = View.VISIBLE
        } else
            itemView.vote_tv_text.text = ""

        itemView.setOnClickListener {

            val intent = Intent(itemView.context, TvDetailActivity::class.java)
            intent.putExtra("Tv_Id", tv.id)
            itemView.context.startActivity(intent)
        }

    }
}