package com.yemreak.kotlinders

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.activity_chronometer.*

class ChronometerActivity : AppCompatActivity(), View.OnClickListener {

    var handler = Handler()
    var runnable : Runnable? = null

    var number = 0
    var isRun = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chronometer)

        bindEvents()
    }

    private fun bindEvents() {
        btn_start.setOnClickListener(this)
        btn_stop.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            btn_start -> start()
            btn_stop -> stop()
        }
    }

    fun start() {
        if (!isRun) {
            number = 0

            runnable = object : Runnable {
                override fun run() {
                    tv_counter.text = "Zamanlayıcı : " + number
                    number++
                    handler.postDelayed(this, 1000) // Konumu önemli değil (başta - sonda), runnable olduğu için içindeki her kodu beklemeden çalıştırır.
                }
            }

            handler.post(runnable)
            isRun = true
        }
    }

    fun stop(){
        handler.removeCallbacks(runnable)
        isRun = false

    }

}
