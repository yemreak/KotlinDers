package com.yemreak.kotlinders

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BaseActivity {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Aktiviteyi başlatma

        bindEvents()
        defineObject()
        setProperties()
    }

    override fun bindEvents() {
        ib_simpson.setOnClickListener { ibSimpsonOnClick() }
        ib_sharedPreference.setOnClickListener { ibSharedPreferenceOnClick() }
        ib_chroneonometer.setOnClickListener { ibChronometerOnClick() }
        ib_favBook.setOnClickListener { ibFavBookOnClick() }
        ib_dataBase.setOnClickListener { ibDataBaseOnClick() }
        ib_myMemory.setOnClickListener { ibMyMemoryOnClick() }
        ib_maps.setOnClickListener { ibMapsOnClick() }
        ib_musicPlayer.setOnClickListener { ibMusicPlayerOnClick() }
        ib_currency.setOnClickListener { ibCurrencyOnClick() }
        ib_fourSquare.setOnClickListener { ibFourSquareOnClick() }
        ib_tabBar.setOnClickListener { ibTabBarOnClick() }
        ib_navigationDrawer.setOnClickListener { ibNavigationDrawerOnClick() }

    }

    override fun defineObject() {

    }

    override fun setProperties() {

    }

    // Event tanımlamaları

    private fun ibSimpsonOnClick() {
        startActivity(Intent(this, CreateSimpsonActivity::class.java))
        showMessage(this, R.string.msg_ibSimpsonOnClick)

    }

    private fun ibSharedPreferenceOnClick() {
        startActivity(Intent(this, SharedPreferenceActivity::class.java))
        showMessage(this, R.string.msg_ibSharedPereferenceOnClick)
    }

    private fun ibChronometerOnClick() {
        startActivity(Intent(this, ChronometerActivity::class.java))
        showMessage(this, R.string.msg_ibChronometerOnClick)
    }

    private fun ibFavBookOnClick() {
        startActivity(Intent(this, FavBookActivity::class.java))
        showMessage(this, R.string.msg_ibFavBookOnClick)
    }

    private fun ibDataBaseOnClick() {
        startAct(this, DataBaseActivity::class.java, R.string.msg_ibDataBaseOnClick)
    }

    private fun ibMyMemoryOnClick() {
        startAct(this, MyMemoryActivity::class.java, R.string.msg_ibMyMemoryOnClick)
    }

    private fun ibMapsOnClick() {
        startAct(applicationContext, MapsActivity::class.java, R.string.msg_ibMapsOnClick)
    }

    private fun ibMusicPlayerOnClick() {
        startAct(applicationContext, MusicPlayerActivity::class.java, R.string.msg_ibMusicPlayerOnClick)
    }

    private fun ibCurrencyOnClick() {
        startAct(applicationContext, CurrencyActivity::class.java, R.string.msg_ibCurrencyOnClick)
    }

    private fun ibFourSquareOnClick() {
        startAct(applicationContext, FourSquareActivity::class.java, R.string.msg_ibFourSquareOnClick)
    }

    private fun ibTabBarOnClick() {
        startAct(applicationContext, TabBarActivity::class.java, R.string.msg_ibTabBarOnClick)
    }

    private fun ibNavigationDrawerOnClick() {
        startAct(applicationContext, NavigationDrawerActivity::class.java, R.string.msg_ibNavigationDrawerOnClick)
    }

}
