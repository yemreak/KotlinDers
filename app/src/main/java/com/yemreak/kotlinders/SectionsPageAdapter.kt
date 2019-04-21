package com.yemreak.kotlinders

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SectionsPageAdapter(fm : FragmentManager, private val context : Context) : FragmentPagerAdapter(fm) {

    private val arr_fragments = ArrayList<Fragment>()
    private val arr_fragmentTitleList = ArrayList<String>()


    /**
     * Fragment ve title bulma.
     * TabBar üstünde çıkan title'ları çekeceğiz ve onları fragmentlere bağlıyacağız.
     */
    fun addFragMent(fragment : Fragment, title : String){
        arr_fragments.add(fragment)
        arr_fragmentTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return arr_fragmentTitleList[position]

    }

    override fun getItem(position: Int): Fragment {
        return arr_fragments[position]
    }

    override fun getCount(): Int {
        return arr_fragments.size
    }

}