@file:Suppress("UNCHECKED_CAST")

package com.example.imdbclone.NetworkCalls

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imdbclone.R
import com.example.imdbclone.Utils.Const
import com.example.imdbclone.adapter.MovieCastOfPerson
import com.example.imdbclone.adapter.TvCastOfPersonAdapter
import com.example.imdbclone.networking.Client.API_KEY
import com.example.imdbclone.networking.Client.retrofitCallBack
import com.example.imdbclone.networking.Client.service
import com.example.imdbclone.networking.Person.CastItemMovie
import com.example.imdbclone.networking.Person.CastItemTv
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_person_detail.*
import kotlinx.android.synthetic.main.movie_cast.*
import java.util.*
import kotlin.collections.ArrayList

class PersonDetailActivity : AppCompatActivity() {

    private var listOfPersonMovie: ArrayList<CastItemMovie> = arrayListOf()
    private var listOfPersonTv: ArrayList<CastItemTv> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_detail)

        val personId = intent.getIntExtra("PersonId", 0)

        layout_coordinator_PersonSelected.visibility = View.GONE
        avi_person_progress_bar_poster.show()

        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        if (isConnected) {
            loadPersonDetail(personId)
            loadPersonMovieCredits(personId)
            loadPersonTVshowCredits(personId)
            upButtonListener()
        } else {
            Toast.makeText(this, "No Active Network Connection", Toast.LENGTH_LONG).show()
        }


    }

    private fun loadPersonDetail(personId: Int) {

        service.getPersonDetail(personId, API_KEY).enqueue(retrofitCallBack { response, throwable ->

            response?.let {
                runOnUiThread {

                    Picasso.get()
                        .load(Const.img_url + response.body()!!.profilePath)
                        .fit()
                        .centerCrop()
                        .into(cast_image)

                    if (PersonDetail_Name_Text.text != null) {
                        PersonDetail_Name_Text.text = response.body()!!.name
                    } else
                        PersonDetail_Name_Text.text = ""

                    if (PersonDetail_Age_Text.text != null) {
                        PersonDetail_Age_Text.text = calculateAgeFromBirthDay(response.body()!!.birthday)
                    } else
                        PersonDetail_Age_Text.text = ""

                    if (PersonDetail_birth_Text.text != null) {
                        PersonDetail_birth_Text.text = response.body()!!.placeOfBirth
                    } else
                        PersonDetail_birth_Text.text = ""

                    if (text_input_Person_Bio.text != null) {
                        text_input_Person_Bio.text = response.body()!!.biography
                    } else
                        text_input_Person_Bio.text = ""


                    setVisibility()
                    avi_person_progress_bar_poster.hide()
                    person_progress_bar.visibility = View.GONE
                }

            }

            throwable?.let {

            }
        })
    }

    private fun calculateAgeFromBirthDay(birthday: String?): String? {

        val calender = Calendar.getInstance(TimeZone.getDefault())

        val year = calender.get(Calendar.YEAR)
        Log.i("year", year.toString())
        val month = calender.get(Calendar.MONTH)
        Log.i("month", month.toString())
        val day = calender.get(Calendar.DAY_OF_MONTH)
        Log.i("day", day.toString())

        val sub = birthday!!.substring(0, 4).toInt()
        Log.i("dob year", sub.toString())

        var age = year - sub

        if (Calendar.DAY_OF_MONTH < birthday.substring(9, 10).toInt() &&
            Calendar.MONTH < birthday.substring(6, 7).toInt()
        ) {

            age--
        }

        return age.toString()
    }


    private fun loadPersonMovieCredits(personId: Int) {

        service.getMovieCreditsOfPerson(personId, API_KEY).enqueue(retrofitCallBack { response, throwable ->

            response?.let {

                runOnUiThread {

                    person_progress_bar.visibility = View.GONE
                    listOfPersonMovie = response.body()!!.castMovie as ArrayList<CastItemMovie>
                    val rcvPersonDetMovAdapter = MovieCastOfPerson(
                        this@PersonDetailActivity,
                        listOfPersonMovie
                    )
                    rcvPerson_Det_Mov.layoutManager = LinearLayoutManager(
                        this@PersonDetailActivity,
                        LinearLayoutManager.HORIZONTAL, false
                    )
                    rcvPerson_Det_Mov.adapter = rcvPersonDetMovAdapter
                }
            }

            throwable?.let {

            }
        })

    }

    private fun loadPersonTVshowCredits(personId: Int) {

        service.getTvCreditsOfPerson(personId, API_KEY).enqueue(retrofitCallBack { response, throwable ->

            response?.let {
                runOnUiThread {

                    person_progress_bar.visibility = View.GONE
                    listOfPersonTv = response.body()!!.castTv as ArrayList<CastItemTv>
                    val rcvPersonDetTvAdapter = TvCastOfPersonAdapter(
                        this@PersonDetailActivity,
                        listOfPersonTv
                    )
                    rcvPerson_Det_Tvshow.layoutManager = LinearLayoutManager(
                        this@PersonDetailActivity,
                        RecyclerView.HORIZONTAL, false
                    )
                    rcvPerson_Det_Tvshow.adapter = rcvPersonDetTvAdapter
                }
            }

            throwable?.let {

            }
        })
    }

    private fun setVisibility() {

        layout_coordinator_PersonSelected.visibility = View.VISIBLE

    }

    private fun upButtonListener() {

        person_Sel_UpButton.setOnClickListener {
            onBackPressed()
        }
    }
}
