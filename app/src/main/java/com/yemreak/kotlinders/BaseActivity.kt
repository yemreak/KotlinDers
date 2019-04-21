package com.yemreak.kotlinders

import android.content.Context
import android.widget.Toast

/**
 * Düzen için eklenmiştir.
 * Created by Yunus Emre on 1/8/2018.
 */
interface BaseActivity : MyFuncs {
    /**
     * Olayların oluşturulacağı metod
     */
    fun bindEvents()

    /**
     * Objelerin tanımlanacağı metod
     */
    fun defineObject()

    /**
     * Özelliklerin ayarlanacağı metod
     */
    fun setProperties()


}