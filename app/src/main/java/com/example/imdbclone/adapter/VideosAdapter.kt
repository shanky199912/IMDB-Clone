package com.example.imdbclone.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imdbclone.R
import com.example.imdbclone.Utils.Const
import com.example.imdbclone.networking.movies.videos.ResultsItem
import com.google.android.youtube.player.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rcviewtrailer.view.*

class VideosAdapter(private val context: Context, private val listtrailer: ArrayList<ResultsItem?>?) :
    RecyclerView.Adapter<VideosHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosHolder {
        return VideosHolder(LayoutInflater.from(context).inflate(R.layout.rcviewtrailer, parent, false))
    }

    override fun getItemCount(): Int {
        return listtrailer!!.size //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: VideosHolder, position: Int) {
        val video = listtrailer?.get(position)
        if (video != null) {
            holder.bind(video)
        }

        //To change body of created functions use File | Settings | File Templates.
    }

}

class VideosHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(video: ResultsItem?) {

        itemView.imageViewItem.visibility = View.GONE
        itemView.playBtn.visibility = View.GONE
        itemView.txtTrailerName.visibility = View.GONE
        Picasso.get()
            .load(Const.image_base_url + video!!.key + Const.image_base_url_thumbnail_max)
            .fit()
            .centerCrop()
            .into(itemView.imageViewItem)


        itemView.imageViewItem.visibility = View.VISIBLE
        itemView.playBtn.visibility = View.VISIBLE

        itemView.crdviewTrailer.playBtn.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(Const.Yotube_base_Url + video.key))
            itemView.context.startActivity(intent)

        }

        /* itemView.playBtn.setOnClickListener {

             itemView.imageViewItem.visibility = View.GONE
             itemView.playBtn.visibility = View.GONE
             itemView.youtubeview.visibility = View.VISIBLE

             val onInitializedListener = object : YouTubePlayer.OnInitializedListener {
                 override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, p1: YouTubePlayer?, p2: Boolean) {
                     p1?.loadVideo(video.key)
                 }

                 override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                     //To change body of created functions use File | Settings | File Templates.
                 }

             }

             itemView.youtubeview.initialize("AIzaSyD8U68VCyn7G4NESyovoWSUGbeIYEXDOk0", onInitializedListener)
         }*/

        if (itemView.txtTrailerName.text != null) {
            itemView.txtTrailerName.text = video.name

        } else
            itemView.txtTrailerName.text = ""

        itemView.txtTrailerName.visibility = View.VISIBLE
    }
}

