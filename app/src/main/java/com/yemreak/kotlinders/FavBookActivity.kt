package com.yemreak.kotlinders

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_fav_book.*

class FavBookActivity : AppCompatActivity(), BaseActivity, AdapterView.OnItemClickListener {


    val arr_names = ArrayList<String>()
    val arr_image = ArrayList<Bitmap>()
    var adapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav_book)
        bindEvents()
        defineObject()
        setProperties()
    }

    override fun bindEvents() {
        lv_names.onItemClickListener = this
    }

    override fun defineObject() {
        defineArrays()

        adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, arr_names) // Diziye listview'e bağlamak için bağlayıcı
    }

    override fun setProperties() {
        lv_names.adapter = adapter // Bağlayıcıyı atama
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        /**
         * İmajlar String'lere nazaran daha büyük boyutlarda oldukları için, intent ile diğer aktivitye taşınmaları programın
         * çalışma hızını düşürecektir bu sebeple globals adlı class'ı oluşturuyoruz.
         * @see Globals
         * @see FavBookDetailActivity.setProperties
        */

        Globals.chosenImage = arr_image[p2]

        /**
         * İsim verisi taşımak için yeterli boyutta olduğu için intent aracılığı ile taşınabilir
         * @see FavBookDetailActivity.setProperties
         */
        startActivity(
                Intent(applicationContext, FavBookDetailActivity::class.java)
                        .putExtra("name", arr_names[p2])
        )
    }

    private fun convertBitmap(resId: Int): Bitmap { // Png'yi bitmap yapma
        return BitmapFactory.decodeResource(applicationContext.resources, resId)
    }

    fun defineArrays() {
        arr_names.add("Pi")
        arr_names.add("Altın oran")

        arr_image.add(convertBitmap(R.drawable.pi_128px))
        arr_image.add(convertBitmap(R.drawable.golder_ratio))
    }
}
