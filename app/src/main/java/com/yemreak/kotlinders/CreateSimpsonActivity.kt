package com.yemreak.kotlinders

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_simpson.*

class CreateSimpsonActivity : AppCompatActivity() {

    private val names = arrayOf("homer","lisa", "bart", "marge")

    var name : String? = null
    var age : Int? = null
    var job : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_simpson)

        bindEvents()
    }

    private fun bindEvents() {
        et_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setImage()
            }

        })
    }

    /**
     * Save butonuna basılınca yapılacaklar
     * @see btn_save
     */
    fun btnSaveClicked(view : View){
        try {
            // Girilen verileri alma
            age = et_age.text.toString().toInt()
            job = et_job.text.toString()
            name = et_name.text.toString()
            val simpson = Simpson(name, age, job)

            Toast.makeText(this, simpson.toString(), Toast.LENGTH_SHORT).show() // Geçici mesaj baloncuğu

        } catch (e : NumberFormatException){
            Toast.makeText(this, R.string.msg_err_age, Toast.LENGTH_SHORT).show() // Geçici mesaj baloncuğu
        }
    }

    /**
     * İsme göre doğru simpson resmini bulma ve gösterme
     */
    fun setImage() {
        var index = 10
        for (i in names.indices){
            if (et_name.text.toString().equals(names[i], true)) {
                index = i
                break
            }
        }
        when (index){
            0 -> iv_image.setImageResource(R.drawable.homer_128px)
            1 -> iv_image.setImageResource(R.drawable.lisa_128px)
            2 -> iv_image.setImageResource(R.drawable.bart_95px)
            3 -> iv_image.setImageResource(R.drawable.marge_91px)
            else -> iv_image.setImageResource(R.drawable.ic_launcher_foreground)
        }
    }
}
