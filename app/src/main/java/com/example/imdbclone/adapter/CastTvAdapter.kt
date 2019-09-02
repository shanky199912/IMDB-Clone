package com.example.imdbclone.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imdbclone.NetworkCalls.PersonDetailActivity
import com.example.imdbclone.R
import com.example.imdbclone.Utils.Const
import com.example.imdbclone.networking.Person.PersonDetail
import com.example.imdbclone.networking.TVshows.CastItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rcviewcast.view.*
import javax.net.ssl.SSLContextSpi

class CastTvAdapter(private val context: Context, private val listTv: ArrayList<CastItem>) :
    RecyclerView.Adapter<TvHolderCast>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvHolderCast {
        return TvHolderCast(LayoutInflater.from(context).inflate(R.layout.rcviewcast, parent, false))
    }

    override fun getItemCount(): Int {
        return listTv.size//To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: TvHolderCast, position: Int) {
        val tv = listTv[position]
        holder.bind(tv)//To change body of created functions use File | Settings | File Templates.
    }

}

class TvHolderCast(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(tv: CastItem) {

        if (tv.profilePath != null) {
            Picasso.get()
                .load(Const.img_url + tv.profilePath)
                .fit()
                .centerCrop()
                .into(itemView.cast_image)
        } else {
            itemView.cast_image.resources.getDrawable(R.drawable.image_not_available)
        }

        if (tv.name!=null && tv.name.trim().isNotEmpty()) {
            itemView.cast_nametv.text = tv.name.trim()
        } else
            itemView.cast_nametv.text = ""

        if (tv.character != null) {
            itemView.cast_character.text = "as ${tv.character}"
        } else
            itemView.cast_character.text = ""


        itemView.setOnClickListener {

            val intent = Intent(itemView.context, PersonDetailActivity::class.java)
            intent.putExtra("PersonId", tv.id)
            itemView.context.startActivity(intent)
        }
    }
}