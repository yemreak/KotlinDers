package com.yemreak.kotlinders

import android.graphics.Bitmap

/**
 * Created by Yunus Emre on 1/13/2018.
 */
class Globals {

    /**
     * Chosen için seçili resmi tutan küresel class
     * @see FavBookActivity.onItemClick
     */
    companion object {
        var chosenImage : Bitmap? = null

        /* olmasa da olur
        fun returnChosenImage() : Bitmap? {
            return chosenImage
        }
        */
    }
}