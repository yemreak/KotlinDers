package com.yemreak.kotlinders

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import kotlinx.android.synthetic.main.activity_my_memory_add.*
import java.io.ByteArrayOutputStream

class MyMemoryAddActivity : AppCompatActivity(), BaseActivity, MyFuncs{
    /**
     * Imajların database de kaydedilmesi mantıklı değildir, mantıklı olan imaj ismini alıp kodda aratıp eklemektir.
     */

    var db : SQLiteDatabase? = null
    var bm_selectedImage : Bitmap? = null

    var error = false

    val NAME_DATABASE = "MyMemory"
    val NAME_TABLE = "myMemory"

    val NAME_VAR_1 = "name"
    val NAME_VAR_2 = "image"
    val NAME_VAR_3 = "detail"

    val TYPE_VAR_1 = "VARCHAR"
    val TYPE_VAR_2 = "BLOB"
    val TYPE_VAR_3 = "VARCHAR"

    val CODE_PERMISSION_RES = 1 // READ_EXTERNAL_STORAGE kodu
    val CODE_RESULT_PICK = 2 // ACTION_PICK kodus

    /**
     * Dosya erişimi için izin alınmıştır
     * AndroidManifest.xml 'e bakın.
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_memory_add)

        bindEvents()
        defineObject()
        setProperties()
    }

    override fun bindEvents() {
        ib_image.setOnClickListener { ibImageOnClick() }
        btn_save.setOnClickListener { btnSaveOnClick() }
    }

    override fun defineObject() {

    }

    override fun setProperties() {
        if (intent.getBooleanExtra("isNew", true)){
            et_name.setText("")
            ib_image.setImageBitmap(BitmapFactory.decodeResource(applicationContext.resources, R.drawable.taptoselect)) // Resmi bitmap yapıp, atıyoruz
            et_detail.setText("")
            btn_save.visibility = View.VISIBLE
        } else {
            et_name.setText(intent.getStringExtra("name"))
            ib_image.setImageBitmap(Globals.chosenImage)
            et_detail.setText(intent.getStringExtra("detail"))
            btn_save.visibility = View.INVISIBLE
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun ibImageOnClick() {
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) { // izni var mı kontrol ediyoruz
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), CODE_PERMISSION_RES) // izin isteme onRequestPermissionsResult
        } else { // iznimiz varsa
           getImageFromMedia()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            CODE_PERMISSION_RES -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {// İzin varsa ve ilk izin alındıysa
                    getImageFromMedia()
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode){
            CODE_RESULT_PICK -> {
                if (resultCode == Activity.RESULT_OK && data != null){
                    try {
                        bm_selectedImage = MediaStore.Images.Media.getBitmap(this.contentResolver, data.data) // Uri ile resmi bitmap yapıyoruz.
                        ib_image.setImageBitmap(bm_selectedImage) // Resmi güncelleme
                    } catch (e : Exception){
                        e.printStackTrace()
                    }
                } else {
                    showMessage(this, R.string.msg_needPermission)
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * Medyadan resim alma işlemi
     */
    private fun getImageFromMedia(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI) // resim alan bir amaç oluşturuyoruz
        startActivityForResult(intent, CODE_RESULT_PICK) // onActivityResult
    }

    private fun btnSaveOnClick() {

        val os = ByteArrayOutputStream() // Byte dizisi oluşturma ortamı tanımlıyoruz
        bm_selectedImage?.compress(Bitmap.CompressFormat.PNG, 10, os) // resmimizi byte'lara dönüştürüyoruz (? olma sebebi nullable olması, en tepede null)
        val arr_byte = os.toByteArray() // bytle dizini belleğe kaydediyoruz

        try {
            db = createDatabase(NAME_DATABASE, this)
            if (db != null) {
                createTable(db!!, NAME_TABLE, "$NAME_VAR_1 $TYPE_VAR_1, $NAME_VAR_2 $TYPE_VAR_2, $NAME_VAR_3 $TYPE_VAR_3")

                // Değişkenlerimi SQL'e aktarmak için gerekli işlemler
                val sqlString = "INSERT INTO $NAME_TABLE ($NAME_VAR_1, $NAME_VAR_2, $NAME_VAR_3) VALUES (?,?, ?)"
                val statement = db!!.compileStatement(sqlString)
                statement.bindString(1, et_name.text.toString()) // String objesini 1. ? 'ne atıyoruz
                statement.bindBlob(2, arr_byte) // BLOB (byteArray) objesini 2. ?'ne atıyoruz
                statement.bindString(3, et_detail.text.toString()) // String objesini 3. ?'ne atıyoruz
                statement.execute() // SQL'li işliyoruz


            } else {
                showMessage(this, "SQL oluşturma hatası")
                error = true
            }
        } catch (e : Exception){
            showMessage(this, e.toString())
        }

        val msg = if(error) "Kaydedilemedi :(" else "Kaydedildi"
        showMessage(applicationContext, msg)

        val intent = Intent(applicationContext, MyMemoryActivity::class.java) // MyMemory'e geçiş
        startActivity(intent)

    }
}
