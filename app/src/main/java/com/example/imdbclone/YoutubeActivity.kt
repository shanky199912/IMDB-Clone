package com.example.imdbclone

import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.Toast
import com.example.imdbclone.Utils.Const
import com.google.android.youtube.player.*
import kotlinx.android.synthetic.main.activity_youtube.*

class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private lateinit var videoId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)

        videoId = intent.getStringExtra("video_id")

        youtubeplayerfragment.initialize(Const.YouTube_Key, this@YoutubeActivity)

        upbuttonlistener()

    }

    override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {

        if (!wasRestored) {
            player!!.cueVideo(videoId)
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
        }

    }

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {

        Toast.makeText(this, p1.toString(), Toast.LENGTH_LONG).show()

        //To change body of created functions use File | Settings | File Templates.
    }

    private fun upbuttonlistener() {
        youtubeUpBtn.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            onBackPressed()
        }
    }
}
