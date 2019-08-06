package com.example.imdbclone.TabbedLayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class PagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm!!) {

    private val movies: ArrayList<String> = ArrayList()
    private val frag: ArrayList<Fragment> = ArrayList()

    override fun getPageTitle(position: Int): CharSequence? {

        return movies[position]

    }

    fun addFragment(fragment: Fragment, title: String) {

        frag.add(fragment)
        movies.add(title)

    }

    override fun getItem(position: Int): Fragment {

        return frag[position]

        //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCount(): Int {

        return movies.size
        //To change body of created functions use File | Settings | File Templates.
    }

}