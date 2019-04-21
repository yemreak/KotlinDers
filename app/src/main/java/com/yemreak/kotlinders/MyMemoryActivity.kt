package com.yemreak.kotlinders

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_my_memory.*

class MyMemoryActivity : AppCompatActivity(), MyFuncs, BaseActivity {

    val arr_names = ArrayList<String>()
    val arr_images = ArrayList<Bitmap>()
    val arr_details = ArrayList<String>()
    var adp_names: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_memory)

        bindEvents()
        defineObject()
        setProperties()
    }

    override fun bindEvents() {
        lv_names.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(applicationContext, MyMemoryAddActivity::class.java)
            // Aktarılacak veriler
            intent.putExtra("name", arr_names[i])
                    .putExtra("detail", arr_details[i])
                    .putExtra("isNew", false)

            Globals.chosenImage = arr_images[i] // İmajlar büyük olduları için Globals kullanıyoruz

            startActivity(intent)
        }
    }

    override fun defineObject() {
        adp_names = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, arr_names) // Adaptör'ün tanımlanması
        lv_names.adapter = adp_names // Adaptörün bağlanması

    }

    /**
     * Buradaki verilerin her biri MyMemoryAddActivity ile aynı olmak zorunda
     * @see MyMemoryAddActivity
     */
    override fun setProperties() {
        try {
            val db = openOrCreateDatabase("MyMemory", Context.MODE_PRIVATE, null)
            db.execSQL("CREATE TABLE IF NOT EXISTS myMemory (name VARCHAR, image BLOB, detail VARCHAR)")

            val cursor = db.rawQuery("SELECT * FROM myMemory ", null) // CUrsor tanımlaması

            // Column indexlerini alıyoruz
            val nameIx = cursor.getColumnIndex("name")
            val imageIx = cursor.getColumnIndex("image")
            val detailIx = cursor.getColumnIndex("detail")

            if (cursor.moveToFirst())  // Cursor'u ilk başa atıyoruz, başa alınma işlemi başarılı olursa true verir
            while (true) {
                arr_names.add(cursor.getString(nameIx)) // ?
                arr_details.add(cursor.getString(detailIx))

                val byteArray = cursor.getBlob(imageIx) // ByteDizisini almak (blob)
                arr_images.add(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)) // Bytedizisini bitmap'e çevirme

                adp_names?.notifyDataSetChanged() // İçindeki verileri güncellemesi için bilgilendiriyoruz

                if (!cursor.moveToNext()) // Cursoru ileri alma, ilerisi yoksa false verir
                    break
            }

        } catch (e: Exception) {
            showMessage(applicationContext, e.toString())
            e.printStackTrace()
        }
    }

    /**
     * Res'deki menüyü aktivitye eklemek için
     * Detaylar için : res / menu / my_memory
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.my_memory, menu) // menüyü ekleme

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_addMemory -> menuAddMemoryClicked()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun menuAddMemoryClicked() {
        val intent = Intent(applicationContext, MyMemoryAddActivity::class.java)
        intent.putExtra("isNew",true)
        startActivity(intent)
        showMessage(applicationContext, R.string.msg_menu_addMemory)
    }

}
