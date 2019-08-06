package com.example.imdbclone.NetworkCalls

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imdbclone.R
import com.example.imdbclone.Utils.Const
import com.example.imdbclone.adapter.CastTvAdapter
import com.example.imdbclone.adapter.SimilarTvAdapter
import com.example.imdbclone.adapter.VideoTvAdapter
import com.example.imdbclone.networking.Client.API_KEY
import com.example.imdbclone.networking.Client.retrofitCallBack
import com.example.imdbclone.networking.Client.service
import com.example.imdbclone.networking.TVshows.CastItem
import com.example.imdbclone.networking.TVshows.GenresItem
import com.example.imdbclone.networking.TVshows.NetworksItem
import com.example.imdbclone.networking.TVshows.ResultsItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.activity_tv_detail.*

@Suppress("UNCHECKED_CAST")
class TvDetailActivity : AppCompatActivity() {

    private var listCast: ArrayList<CastItem> = arrayListOf()
    private var listShows: ArrayList<ResultsItem> = arrayListOf()
    private var listTrailer: ArrayList<com.example.imdbclone.networking.TVshows.videos.ResultsItem> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_detail)

        setSupportActionBar(toolbar_tv_Sel)
        supportActionBar?.title = ""

        val tvId = intent.getIntExtra("Tv_Id", -1)
        if (tvId == -1) finish()

        tv_layout_detailTv.visibility = View.GONE

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        val isConnected = activeNetworkInfo?.isConnected == true

        if (isConnected) {
            loadTvDetails(tvId)
            loadTvVideos(tvId)
            loadTvCast(tvId)
            loadTvShowsSimilar(tvId)
            shareButtonListener()
            upButtonListener()
        } else
            Toast.makeText(this, "No Network Connection", Toast.LENGTH_LONG).show()


    }

    private fun loadTvDetails(tvId: Int) {

        service.getTvDetails(tvId, API_KEY).enqueue(retrofitCallBack { response, throwable ->

            response?.let {
                runOnUiThread {

                    Picasso.get()
                        .load(Const.img_url + response.body()!!.backdropPath)
                        .fit()
                        .centerCrop()
                        .into(tv_imageViewBack)

                    Picasso.get()
                        .load(Const.img_url + response.body()!!.posterPath)
                        .fit()
                        .centerCrop()
                        .into(tv_imageViewPoster)

                    if (text_tv_Name.text != null) {
                        text_tv_Name.text = response.body()!!.name!!.trimEnd()
                    } else
                        text_tv_Name.text = ""

                    setGenreTv(response.body()!!.genres)

                    if (tv_SelTxtRat.text != null) {
                        tv_SelTxtRat.text = response.body()!!.voteAverage.toString() + "/10"
                        tv_SelTxtRat.visibility = View.VISIBLE
                        tv_SelImgStar.visibility = View.VISIBLE
                    } else
                        tv_SelTxtRat.text = ""

                    if (tv_SelTxtDes.text != null) {
                        tv_SelTxtDes.text = response.body()!!.overview
                    } else
                        tv_SelTxtDes.text = ""

                    if (tv_SelTxt_FirstAirdate.text != null) {
                        tv_SelTxt_FirstAirdate.text = "First Air Date : " + response.body()!!.firstAirDate
                    } else
                        tv_SelTxt_FirstAirdate.text = ""

                    if (tv_SelTxt_Runtime.text != null) {
                        tv_SelTxt_Runtime.text = "Runtime : ${response.body()!!.episodeRunTime.toString().substring(
                            1,
                            response.body()!!.episodeRunTime.toString().length - 1
                        )} min(s)"
                    } else
                        tv_SelTxt_Runtime.text = ""

                    if (tv_SelTxt_Status.text != null) {
                        tv_SelTxt_Status.text = "Status : " + response.body()!!.status
                    } else
                        tv_SelTxt_Status.text = ""

                    if (tv_SelTxt_OriginCountry.text != null) {
                        tv_SelTxt_OriginCountry.text =
                            "Origin Country : " + setOriginCountry(response.body()!!.originCountry)
                    } else
                        tv_SelTxt_OriginCountry.text = ""

                    if (tv_SelTxt_Networks.text != null) {
                        tv_SelTxt_Networks.text = "Networks : " + setNetwork(response.body()!!.networks)
                    } else
                        tv_SelTxt_Networks.text = ""

                    visibilitySet()
                    avi_tv_progress_bar_backdrop.hide()
                    avi_tv_progress_bar_poster.hide()
                    tv_progress_bar_detail.visibility = View.GONE

                }
            }
            throwable?.let {

            }
        })
    }

    private fun setOriginCountry(originCountry: List<String?>?): CharSequence? {

        var country = ""
        for (i in 0 until originCountry!!.size) {
            country += originCountry[i] + " ,"
        }

        return country.substring(0, country.length - 2)
    }

    private fun setNetwork(network: List<NetworksItem?>?): CharSequence? {

        var name: String? = ""
        for (i in 0 until network!!.size) {

            name += network[i]!!.name + " ,"
        }
        return name!!.substring(0, name.length - 2)
    }

    private fun setGenreTv(genres: List<GenresItem?>?) {

        var genreStr = ""
        for (i in 0 until genres!!.size) {

            genreStr += genres[i]!!.name + " ,"
        }

        if (genreStr.isNotEmpty()) {
            text_tv_genre.text = genreStr.substring(0, genreStr.length - 2)
        } else
            text_tv_genre.text = ""
    }

    private fun loadTvVideos(tvId: Int) {

        service.getTvVideos(tvId, API_KEY).enqueue(retrofitCallBack { response, throwable ->
            response?.let {
                runOnUiThread {
                    tv_progress_bar_detail.visibility = View.GONE
                    listTrailer =
                        response.body()!!.results as ArrayList<com.example.imdbclone.networking.TVshows.videos.ResultsItem>
                    val trailerAdapter = VideoTvAdapter(this@TvDetailActivity, listTrailer)
                    rcv_tv_Trailers.layoutManager =
                        LinearLayoutManager(this@TvDetailActivity, LinearLayoutManager.HORIZONTAL, false)
                    rcv_tv_Trailers.adapter = trailerAdapter
                }
            }
            throwable?.let {

            }
        })
    }

    private fun loadTvShowsSimilar(tvId: Int) {

        service.getTvSimilar(tvId, API_KEY, 1).enqueue(retrofitCallBack { response, throwable ->

            response?.let {
                runOnUiThread {
                    tv_progress_bar_detail.visibility = View.GONE
                    listShows = response.body()!!.results as ArrayList<ResultsItem>
                    val showAdapter = SimilarTvAdapter(this@TvDetailActivity, listShows)
                    rcv_tv_SimilarShow.layoutManager =
                        LinearLayoutManager(this@TvDetailActivity, LinearLayoutManager.HORIZONTAL, false)
                    rcv_tv_SimilarShow.adapter = showAdapter
                }
            }
            throwable?.let {

            }
        })
    }

    private fun loadTvCast(tvId: Int) {

        service.getTvCredits(tvId, API_KEY).enqueue(retrofitCallBack { response, throwable ->
            response?.let {
                runOnUiThread {
                    tv_progress_bar_detail.visibility = View.GONE
                    listCast = response.body()!!.cast as ArrayList<CastItem>
                    val cast_tv_adapter = CastTvAdapter(this@TvDetailActivity, listCast)
                    rcv_tv_Cast.layoutManager =
                        LinearLayoutManager(this@TvDetailActivity, LinearLayoutManager.HORIZONTAL, false)
                    rcv_tv_Cast.adapter = cast_tv_adapter
                }
            }
        })
    }

    private fun shareButtonListener() {

        tv_SelShareBtn.setOnClickListener {

            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBodyText = "Check it out. Your message goes here"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText)
            startActivity(Intent.createChooser(sharingIntent, "Shearing Options"))
        }
    }

    private fun upButtonListener() {
        tv_SelUpButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun visibilitySet() {
        tv_layout_detailTv.visibility = View.VISIBLE
    }
}
