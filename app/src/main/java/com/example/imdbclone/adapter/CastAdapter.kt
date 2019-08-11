package com.example.imdbclone.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imdbclone.NetworkCalls.PersonDetailActivity
import com.example.imdbclone.R
import com.example.imdbclone.networking.movies.CastItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rcviewcast.view.*

class CastAdapter(private val context: Context, private val listCast: ArrayList<CastItem?>?) :
    RecyclerView.Adapter<CastViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder(LayoutInflater.from(context).inflate(R.layout.rcviewcast, parent, false))
    }

    override fun getItemCount(): Int {
        return listCast?.size!! //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val cast = listCast?.get(position)
        cast?.let { holder.bind(it) }

    }

}

class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(cast: CastItem) {

        itemView.cast_image.visibility = View.GONE
        itemView.cast_character.visibility = View.GONE
        itemView.cast_nametv.visibility = View.GONE
        Picasso.get()
            .load("https://image.tmdb.org/t/p/original" + cast.profilePath)
            .fit()
            .centerCrop()
            .into(itemView.cast_image)

        if (cast.name !=null && itemView.cast_nametv.text.trim().isNotEmpty()) {

            itemView.cast_nametv.text =  cast.name.trim()
        } else
            itemView.cast_nametv.text = ""

        if (cast.character != null && itemView.cast_character.text.trim().isNotEmpty()) {
            itemView.cast_character.text = "as ${cast.character.trim()}"
        } else {
            itemView.cast_character.text = ""
        }

        itemView.cast_image.visibility = View.VISIBLE
        itemView.cast_character.visibility = View.VISIBLE
        itemView.cast_nametv.visibility = View.VISIBLE

        itemView.cast_image.setOnClickListener {

            val intent = Intent(itemView.context, PersonDetailActivity::class.java)
            intent.putExtra("PersonId", cast.id)
            itemView.context.startActivity(intent)
        }
    }
}