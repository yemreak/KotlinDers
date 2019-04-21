package com.yemreak.kotlinders

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_shared_preference.*

class SharedPreferenceActivity : AppCompatActivity() {

    var sharedPreference: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_preference)

        /**
         * Küçük veri işlemleri için kullanılır büyük veriker için SQLite kullanılır.
         * @see DataBaseActivity
         */
        sharedPreference = getSharedPreferences(packageName, Context.MODE_PRIVATE) // Verileri alma
    }


    fun btnSaveClicked(view: View) {
        sharedPreference?.edit()?.putString(et_key.text.toString(), et_data.text.toString())?.apply()
        tv_text.text = et_data.text.toString()
    }

    fun btnDelClicked(view: View) {
        sharedPreference?.edit()?.remove(et_key.text.toString())?.apply()
        tv_text.setText("Veri silidi")
    }

    fun btnGetClicked(view: View) {
        tv_text.text = sharedPreference?.getString(et_key.text.toString(), "Veri yok")
    }
}
