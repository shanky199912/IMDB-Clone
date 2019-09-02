package com.example.imdbclone.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FavPagerAdapter(fm: FragmentManager?, val context: Context) : FragmentPagerAdapter(
    fm!!
) {

    private val frag: ArrayList<Fragment> = arrayListOf()
    private val listTitle:ArrayList<String> = arrayListOf()

    override fun getItem(position: Int): Fragment {

        return frag[position]

    }

    fun addFrag(fragment: Fragment, title:String) {

        frag.add(fragment)
        listTitle.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return listTitle[position]
    }

    override fun getCount(): Int {
        return 2//To change body of created functions use File | Settings | File Templates.
    }

}