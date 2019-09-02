package com.example.imdbclone.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imdbclone.NetworkCalls.MovieDetail
import com.example.imdbclone.NetworkCalls.PersonDetailActivity
import com.example.imdbclone.NetworkCalls.TvDetailActivity
import com.example.imdbclone.R
import com.example.imdbclone.Utils.Const
import com.example.imdbclone.networking.Search.ResultsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rcview_search.view.*

class SearchResultAdapter(private val context: Context, private val mlistSearch: ArrayList<ResultsItem>) :
    RecyclerView.Adapter<SearchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {

        return SearchViewHolder(LayoutInflater.from(context).inflate(R.layout.rcview_search, parent, false))
    }

    override fun getItemCount(): Int {

        return mlistSearch.size
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        if (mlistSearch[position].posterPath != null) {
            Picasso.get()
                .load(Const.img_url + mlistSearch[position].posterPath)
                .fit()
                .centerCrop()
                .into(holder.itemView.image_view_poster_search)
        } else
            holder.itemView.image_view_poster_search.resources.getDrawable(R.drawable.image_not_available)

        if (mlistSearch[position].title != null &&
            mlistSearch[position].title!!.trim().isNotEmpty()
            && mlistSearch[position].mediaType.equals("movie")
        ) {
            holder.itemView.text_view_name_search.text = mlistSearch[position].title!!.trim()
        } else if (mlistSearch[position].name != null &&
            mlistSearch[position].name!!.trim().isNotEmpty()
            && mlistSearch[position].mediaType.equals("tv")
        ) {
            holder.itemView.text_view_name_search.text = mlistSearch[position].name!!.trim()
        } else if (mlistSearch[position].name != null &&
            mlistSearch[position].name!!.trim().isNotEmpty()
            && mlistSearch[position].mediaType.equals("person")
        ) {
            holder.itemView.text_view_name_search.text = mlistSearch[position].name!!.trim()
        } else
            holder.itemView.text_view_name_search.text = ""

        if (mlistSearch[position].mediaType != null && mlistSearch[position].mediaType.equals("movie")) {
            holder.itemView.text_view_media_type_search.text = mlistSearch[position].mediaType
        } else if (mlistSearch[position].mediaType != null && mlistSearch[position].mediaType.equals("person")) {
            holder.itemView.text_view_media_type_search.text = mlistSearch[position].mediaType
        } else if (mlistSearch[position].mediaType != null && mlistSearch[position].mediaType.equals("tv")) {
            holder.itemView.text_view_media_type_search.text = mlistSearch[position].mediaType
        } else
            holder.itemView.text_view_media_type_search.text = ""


        if (mlistSearch[position].overview != null && mlistSearch[position].overview!!.trim().isNotEmpty()) {
            holder.itemView.text_view_overview_search.text = mlistSearch[position].overview!!.trim()
        } else
            holder.itemView.text_view_overview_search.text = ""

        if (mlistSearch[position].releaseDate != null &&
            mlistSearch[position].releaseDate!!.trim().isNotEmpty()
            && mlistSearch[position].mediaType.equals("movie")
        ) {
            holder.itemView.text_view_year_search.text = mlistSearch[position].releaseDate!!.substring(0, 4)
        } else if (mlistSearch[position].firstAirDate != null &&
            mlistSearch[position].firstAirDate!!.trim().isNotEmpty()
            && mlistSearch[position].mediaType.equals("tv")
        ) {
            holder.itemView.text_view_year_search.text = mlistSearch[position].firstAirDate!!.substring(0, 4)
        } else
            holder.itemView.text_view_year_search.text = ""

        val search = mlistSearch[position]
        holder.bind(search)
    }

}

class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(search: ResultsItem) {

        itemView.card_view_search.setOnClickListener {

            when {
                search.mediaType.equals("movie") -> {

                    val intent = Intent(itemView.context, MovieDetail::class.java)
                    intent.putExtra("MovieId", search.id)
                    itemView.context.startActivities(arrayOf(intent))

                }
                search.mediaType.equals("tv") -> {

                    val intent = Intent(itemView.context, TvDetailActivity::class.java)
                    intent.putExtra("Tv_Id", search.id)
                    itemView.context.startActivities(arrayOf(intent))

                }
                search.mediaType.equals("person") -> {

                    val intent = Intent(itemView.context, PersonDetailActivity::class.java)
                    intent.putExtra("PersonId", search.id)
                    itemView.context.startActivities(arrayOf(intent))
                }
            }
        }
    }


}