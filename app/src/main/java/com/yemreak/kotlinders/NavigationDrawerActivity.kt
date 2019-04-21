package com.yemreak.kotlinders

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_navigation_drawer.*

class NavigationDrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)



        // NavigationView'in açılması için buton ayarlama
        val toggle = ActionBarDrawerToggle(
                this, dl_main, toolbar, R.string.common_open_on_phone, R.string.msg_ibNavigationDrawerOnClick)

        dl_main.addDrawerListener(toggle) // Listener'i ekleme
        toggle.syncState() // Senktonize olma
        actionBar.setDisplayHomeAsUpEnabled(true)


        // İlk açıldığında null çünkü yok (?)
        if(nv_main == null)
            println("nul bu")
        else
            nv_main.setNavigationItemSelectedListener(this)

    }

    // NavigationView'daki item'lara tıklanınca çalışan metod
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_bookmarks -> {

            }
            R.id.nav_favourites -> {

            }
            R.id.nav_foursquare -> {

            }
            R.id.nav_sql -> {

            }
            R.id.nav_simpsons -> {

            }
        }

        dl_main.closeDrawer(GravityCompat.START) // Drawer layoutu kapatma
        return true
    }
}
