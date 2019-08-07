package com.example.imdbclone.NetworkCalls

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imdbclone.R
import com.example.imdbclone.Utils.Const
import com.example.imdbclone.adapter.CastAdapter
import com.example.imdbclone.adapter.SimilarMoviesAdapter
import com.example.imdbclone.adapter.VideosAdapter
import com.example.imdbclone.networking.Client.API_KEY
import com.example.imdbclone.networking.Client.retrofitCallBack
import com.example.imdbclone.networking.Client.service
import com.example.imdbclone.networking.movies.*
import com.example.imdbclone.networking.movies.videos.VideoResponse
import com.google.android.material.appbar.AppBarLayout
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.rcviewtrailer.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetail : AppCompatActivity() {

    private var listCast: ArrayList<CastItem?>? = arrayListOf()
    private var listTrailer: ArrayList<com.example.imdbclone.networking.movies.videos.ResultsItem?>? = arrayListOf()
    private var listMovie: ArrayList<ResultsItem?>? = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        setSupportActionBar(toolbarMovieSel)
        toolbarMovieSel.title = ""

        val mMovieId = intent.getIntExtra("MovieId", -1)
        if (mMovieId == -1) finish()

        layout_coordinator_MovieSelected.visibility = View.GONE
        avi_progress_bar_backdrop.hide()
        avi_progress_bar_poster.hide()

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        val isConnected = activeNetworkInfo?.isConnected == true

        if (isConnected) {
            loadMovieDetails(mMovieId)
            loadMovieCast(mMovieId)
            loadMovieTrailers(mMovieId)
            loadSimilarMovies(mMovieId)
            backButtonListener()
            shareButtonListener()
            favButtonListener()
        } else
            Toast.makeText(this, "No Network Connection", Toast.LENGTH_LONG).show()

    }

    /**
     *  A function which makes the NetworkCall to the API
     *  Used Retrofit for making the networkCall
     *  The response is passed to the MovieDetailsItem..
     */
    private fun loadMovieDetails(mMovieId: Int) {

        avi_progress_bar_backdrop.show()
        avi_progress_bar_poster.show()

        service.getMovieDetails(mMovieId, API_KEY).enqueue(retrofitCallBack { response, throwable ->

            response?.let {

                runOnUiThread {

                    movie_progress_bar_detail.visibility = View.GONE
                    Picasso.get()
                        .load("https://image.tmdb.org/t/p/original" + response.body()?.backdropPath)
                        .fit()
                        .centerCrop()
                        .into(imageViewBack)

                    avi_progress_bar_backdrop.hide()

                    Picasso.get()
                        .load("https://image.tmdb.org/t/p/original" + response.body()?.posterPath)
                        .fit()
                        .centerCrop()
                        .into(imageViewMovie)

                    avi_progress_bar_poster.hide()
                    /* setSupportActionBar(toolbarMovieSel)
                     toolbarMovieSel.title = response.body()!!.title*/

                    layoutAppbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOfset ->

                        if (appBarLayout.totalScrollRange + verticalOfset == 0) {
                            if (response.body()?.title != null)
                                collapsingToolbar_MovieDetail.title = response.body()!!.title
                            else
                                collapsingToolbar_MovieDetail.title = ""
                            toolbarMovieSel.visibility = View.VISIBLE
                        } else {
                            collapsingToolbar_MovieDetail.title = ""
                            toolbarMovieSel.visibility = View.INVISIBLE
                        }
                    })

                    textMovieName.visibility = View.GONE

                    if (textMovieName.text != null) {

                        textMovieName.text = response.body()?.originalTitle
                    } else
                        textMovieName.text = ""

                    textMovieGenre.visibility = View.GONE

                    setGenre(response.body()?.genres)

                    movSelTxtRat.visibility = View.GONE

                    if (movSelTxtRat.text != null) {
                        movSelTxtRat.text = response.body()?.voteAverage.toString() + "/10"
                    } else {
                        movSelTxtRat.text = ""
                        movSelImgStar.visibility = View.GONE
                    }
                    if (response.body()?.voteAverage == null) {
                        movSelTxtRat.text = ""
                        movSelImgStar.visibility = View.GONE
                    }

                    movSelTxtDes.visibility = View.GONE
                    if (movSelTxtDes.text != null) {
                        movSelTxtDes.text = response.body()?.overview
                    } else
                        movSelTxtDes.text = ""

                    movSelTxtDuration.visibility = View.GONE
                    movSelTxtReleasedate.visibility = View.GONE

                    if (movSelTxtDuration.text != null) {
                        movSelTxtDuration.text = "Duration: ${response.body()?.runtime.toString()} minutes"
                    } else
                        movSelTxtDuration.text = ""

                    if (movSelTxtReleasedate.text != null) {
                        movSelTxtReleasedate.text = "Release Date: ${response.body()?.releaseDate.toString()}"
                    } else
                        movSelTxtReleasedate.text = ""

                    setVisibility()
                }
            }

            throwable?.let {

            }
        })
    }

    /**
     *  A function which makes the NetworkCall to the API
     *  Used Retrofit for making the networkCall
     *  The response is passed to the Adapter of Videos
     *  YouTube Sdk Implementation is pending..
     */
    private fun loadMovieTrailers(mMovieId: Int) {

        /*
 *//*
                    val playbtn = findViewById<ImageButton>(R.id.playBtn)
                    val youTubePlayerFragment =
                        supportFragmentManager.findFragmentById(R.id.youtubeview) as YouTubePlayerSupportFragment

                    val onInitializedListener = object : YouTubePlayer.OnInitializedListener {
                        override fun onInitializationSuccess(
                            p0: YouTubePlayer.Provider?,
                            p1: YouTubePlayer?,
                            p2: Boolean
                        ) {

                            p1!!.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
                            p1.loadVideo(listTrailer?.get(0)!!.key)

                        }

                        override fun onInitializationFailure(
                            p0: YouTubePlayer.Provider?,
                            p1: YouTubeInitializationResult?
                        ) {

                            Log.e("YoutubeResponse", "Youtube Player View initialization failed")
                            //To change body of created functions use File | Settings | File Templates.
                        }

                    }

                    youTubePlayerFragment.initialize(Const.YouTube_Key, onInitializedListener)*//*


                }

                //To change body of created functions use File | Settings | File Templates.
            }

        })*/

        service.getMovieVideos(mMovieId, API_KEY).enqueue(retrofitCallBack { response, throwable ->

            response?.let {
                runOnUiThread {
                    if (response.isSuccessful) {
                        movie_progress_bar_detail.visibility = View.GONE
                        listTrailer =
                            response.body()?.results as ArrayList<com.example.imdbclone.networking.movies.videos.ResultsItem?>?
                        val trailerAdapter = VideosAdapter(this@MovieDetail, listTrailer)
                        rcvTrailers.layoutManager = LinearLayoutManager(
                            this@MovieDetail,
                            LinearLayoutManager.HORIZONTAL, false
                        )
                        rcvTrailers.adapter = trailerAdapter
                    } else {

                        movSelTxtTrailer.visibility = View.INVISIBLE
                        rcvTrailers.visibility = View.INVISIBLE
                    }
                }
            }

            throwable?.let {

                movSelTxtTrailer.visibility = View.INVISIBLE
                rcvTrailers.visibility = View.INVISIBLE
            }
        })
    }

    /**
     *  A function which makes the NetworkCall to the API
     *  Used Retrofit for making the networkCall
     *  The response is passed to the Adapter of MovieCast..
     */
    private fun loadMovieCast(mMovieId: Int) {

        service.getMovieCast(mMovieId, API_KEY).enqueue(retrofitCallBack { response, throwable ->

            response?.let {
                runOnUiThread {

                    movie_progress_bar_detail.visibility = View.GONE
                    listCast = response.body()?.cast

                    val castAdapter = CastAdapter(this@MovieDetail, listCast)
                    rcvCast.layoutManager = LinearLayoutManager(
                        this@MovieDetail,
                        LinearLayoutManager.HORIZONTAL, false
                    )
                    rcvCast.adapter = castAdapter
                }
            }

            throwable?.let {

                movSelTxtCast.visibility = View.INVISIBLE
                rcvCast.visibility = View.INVISIBLE
            }
        })
    }

    /**
     *  A function which makes the NetworkCall to the API
     *  Used Retrofit for making the networkCall
     *  The response is passed to the Adapter of SimilarMovies..
     */
    private fun loadSimilarMovies(mMovieId: Int) {

        service.getSimilarMovies(mMovieId, API_KEY, 1).enqueue(retrofitCallBack { response, throwable ->

            response?.let {
                runOnUiThread {

                    movie_progress_bar_detail.visibility = View.GONE
                    listMovie = response.body()?.results
                    val movieAdapter = SimilarMoviesAdapter(this@MovieDetail, listMovie)
                    rcvSimilarMovies.layoutManager = LinearLayoutManager(
                        this@MovieDetail,
                        LinearLayoutManager.HORIZONTAL, false
                    )
                    rcvSimilarMovies.adapter = movieAdapter
                }
            }

            throwable?.let {

                movTxtSimilarMov.visibility = View.INVISIBLE
                rcvSimilarMovies.visibility = View.INVISIBLE
            }
        })
    }

    /**
     * Code for displaying the Genres of the Movies in the MovieDetail
     * Substring is used to remove the extra comma and space from the lastItem..
     */
    private fun setGenre(genres: List<GenresItem?>?) {

        var genreStr = ""
        for (i in 0 until genres!!.size) {

            genreStr += genres[i]!!.name + " ,"

        }

        if (genreStr.isNotEmpty()) {
            textMovieGenre.text = (genreStr.substring(0, genreStr.length - 2))
        } else
            textMovieGenre.text = ""
    }

    /**
     * Code for the upButton in MovieDetail
     * Listener is set to the button and the onBackPressedMethod is called in it..
     */
    private fun backButtonListener() {

        movSelUpButton.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            onBackPressed()
        }
    }

    /**
     * Set the visibility to visible for the movieDetail items
     * To note- the visibilities of recyclerViews are set in their respective Adapters.
     */
    private fun setVisibility() {

        layout_coordinator_MovieSelected.visibility = View.VISIBLE
        imageViewMovie.visibility = View.VISIBLE
        imageViewBack.visibility = View.VISIBLE
        textMovieGenre.visibility = View.VISIBLE
        textMovieName.visibility = View.VISIBLE
        movSelImgStar.visibility = View.VISIBLE
        movSelTxtDes.visibility = View.VISIBLE
        movSelTxtDuration.visibility = View.VISIBLE
        movSelTxtReleasedate.visibility = View.VISIBLE
        movSelTxtRat.visibility = View.VISIBLE
        avi_progress_bar_backdrop.show()
        avi_progress_bar_poster.show()

    }

    private fun shareButtonListener() {

        movSelShareBtn.setOnClickListener {

            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBodyText = "Check it out. Your message goes here"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText)
            startActivity(Intent.createChooser(sharingIntent, "Shearing Options"))

        }
    }

    private fun favButtonListener() {

        movSelFav.setOnClickListener {
            it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            Toast.makeText(this, "Add to Favourite feature to be out soon!", Toast.LENGTH_LONG).show()
        }
    }
}
