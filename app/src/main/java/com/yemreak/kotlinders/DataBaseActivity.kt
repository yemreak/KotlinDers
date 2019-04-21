package com.yemreak.kotlinders

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.webkit.WebResourceRequest
import kotlinx.android.synthetic.main.activity_data_base.*
import android.webkit.WebView
import android.webkit.WebViewClient



class DataBaseActivity : AppCompatActivity() {

    var database: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_base)

        wv_main!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }

        wv_main.loadUrl(getString(R.string.uri_blog_kotlin_sqlWithDatabase))

    }

    private fun databaseOluştur() {
        try {
            database = openOrCreateDatabase("Datas", Context.MODE_PRIVATE, null)

            if (database != null)
                database!!.execSQL("CREATE TABLE IF NOT EXISTS datas (name VARCHAR, age INT(2))") // INT(2) ile 2 rakam olacağını belli ediyoruz

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun databaseVeriEkle() {
        if (database != null) {
            try {
                database!!.execSQL("INSERT INTO datas (name, age) VALUES ('Yunus' , 20)")
                database!!.execSQL("INSERT INTO datas (name, age) VALUES ('Emre', 15)")

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun databaseVeriFiltreleme(){
        if (database != null){

            database!!.execSQL("SELECT * FROM datas WHERE name = 'Yunus'") // Yunus isimli olan dataları alır

            database!!.execSQL("SELECT * FROM datas WHERE name LIKE '%s'") // sonun 's' harfi olanları alır

            database!!.execSQL("SELECT * FROM datas WHERE name LIKE 'y%'") // başında 'y' harfi olanları alır

            database!!.execSQL("SELECT * FROM datas WHERE name LIKE '%u%'") // içinde 'u' harfi olanları alır
        }
    }

    private fun databaseVeriGüncelleYadaSil(){
        if (database != null){

            database!!.execSQL("UPDATE datas SET age = 21 WHERE name = 'Yunus'") // Veri güncelleme

            database!!.execSQL("DELETE FROM datas WHERE name = 'Emre'") // Veri silme
        }
    }

    private fun databaseOku() {
        if (database != null) {
            val cursor = database!!.rawQuery("SELECT * FROM datas", null)

            val nameIndex = cursor.getColumnIndex("name")
            val ageIndex = cursor.getColumnIndex("age")

            cursor.moveToFirst()

            while (cursor != null) {
                println("İsim : ${cursor.getString(nameIndex)}")
                println("Yaş : ${cursor.getString(ageIndex)}")

                cursor.moveToNext()
            }


        }
    }
}
