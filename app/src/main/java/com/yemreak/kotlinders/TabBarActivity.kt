package com.yemreak.kotlinders

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.activity_tab_bar.*

class TabBarActivity : AppCompatActivity() {

    private lateinit var sectionsPageAdapter: SectionsPageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_bar)

        sectionsPageAdapter = SectionsPageAdapter(supportFragmentManager, this)

        setupPager(vp_main)
        tabLayout.setupWithViewPager(vp_main)

        tabLayout.getTabAt(0)!!.icon = getDrawable(R.mipmap.ic_launcher)
    }


    private fun setupPager(viewPager: ViewPager){
        val adapter = SectionsPageAdapter(supportFragmentManager, this)
        adapter.addFragMent(HotFragment(), "Hot")
        adapter.addFragMent(FreshFragment(), "Fresh")
        adapter.addFragMent(TrendingFragment(), "Trending")
        viewPager.adapter = adapter

    }
}
