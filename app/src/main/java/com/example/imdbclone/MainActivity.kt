package com.example.imdbclone

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.widget.Toast
import com.example.imdbclone.NetworkCalls.aboutFragment
import com.example.imdbclone.NetworkCalls.favoritesFragment
import com.example.imdbclone.NetworkCalls.movieFragment
import com.example.imdbclone.NetworkCalls.tvShowFragment
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var doubleBackToExitPressedOnce: Boolean = false
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        if (sharedPref.getBoolean("First Time Launch", true)) {

        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        navView.setNavigationItemSelectedListener(this)

        navView.setCheckedItem(R.id.nav_movies)
        supportActionBar?.title = "Movies"
        supportFragmentManager.beginTransaction()
            .add(R.id.container, movieFragment())
            .commit()

        search_view.setOnClickListener {
            onSearchRequested()
        }

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

    }


    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }

            doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Press Again To Exit", Toast.LENGTH_SHORT).show()

            Handler().postDelayed({
                doubleBackToExitPressedOnce = false
            }, 2000)
        }

        if (search_view.isSearchOpen) {
            search_view.closeSearch()
        } else
            super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

        val item = menu.findItem(R.id.action_search)
        search_view.setMenuItem(item)
        return true
    }

    override fun onSearchRequested(): Boolean {
        onPause()
        return super.onSearchRequested()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        if (toggle.onOptionsItemSelected(item)){
            return true
        }

        return when (item.itemId) {
            R.id.nav_view -> true
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_movies -> {
                // Handle the Movie - open the movie fragment
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, movieFragment()).commit()
                val toolbar: Toolbar = findViewById(R.id.toolbar)
                setSupportActionBar(toolbar)
                supportActionBar?.title = "Movies"

            }
            R.id.nav_tvShows -> {
                //open up tv show fragment
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, tvShowFragment()).commit()
                val toolbar: Toolbar = findViewById(R.id.toolbar)
                setSupportActionBar(toolbar)
                supportActionBar?.title = "Tv Shows"
            }
            R.id.nav_favorites -> {
                //open favorites fragment
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, favoritesFragment()).commit()
                val toolbar: Toolbar = findViewById(R.id.toolbar)
                setSupportActionBar(toolbar)
                supportActionBar?.title = "Favorites"
            }
            R.id.nav_about -> {
                //open about fragment
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, aboutFragment()).commit()
                val toolbar: Toolbar = findViewById(R.id.toolbar)
                setSupportActionBar(toolbar)
                supportActionBar?.title = "About"
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
