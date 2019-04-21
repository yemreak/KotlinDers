package com.yemreak.kotlinders

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_fav_book_detail.*

class FavBookDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav_book_detail)

        setProperties()
    }

    fun setProperties(){
        tv_name.text = intent.getStringExtra("name")
        /**
         * Global değişkenden özel tanımladığımız yerden chosenImage'i alıyoruz.
         * @see Globals
         */
        iv_image.setImageBitmap(Globals.chosenImage)
    }
}
