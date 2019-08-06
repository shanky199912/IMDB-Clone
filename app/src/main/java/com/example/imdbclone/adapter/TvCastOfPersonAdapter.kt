package com.example.imdbclone.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imdbclone.NetworkCalls.TvDetailActivity
import com.example.imdbclone.R
import com.example.imdbclone.Utils.Const
import com.example.imdbclone.networking.Person.CastItemTv
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_cast.view.*

class TvCastOfPersonAdapter(private val context: Context, private val listCastItemTv: ArrayList<CastItemTv>) :
    RecyclerView.Adapter<TvCastViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvCastViewHolder {

        return TvCastViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_cast, parent, false))
    }

    override fun getItemCount(): Int {
        return listCastItemTv.size
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: TvCastViewHolder, position: Int) {
        val cast = listCastItemTv[position]
        holder.bind(cast)//To change body of created functions use File | Settings | File Templates.
    }

}

class TvCastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(castTv: CastItemTv) {

        Picasso.get()
            .load(Const.img_url + castTv.posterPath)
            .fit()
            .centerCrop()
            .into(itemView.img_movie_cast)

        if (itemView.text_movie_name.text != null) {
            itemView.text_movie_name.text = castTv.originalName
        } else
            itemView.text_movie_name.text = ""


        if (itemView.text_movie_char.text != null) {
            itemView.text_movie_char.text = "as ${castTv.character}"
        } else
            itemView.text_movie_char.text = ""


        itemView.setOnClickListener {

            val intent = Intent(itemView.context, TvDetailActivity::class.java)
            intent.putExtra("Tv_Id", castTv.id)
            itemView.context.startActivity(intent)
        }

    }
}