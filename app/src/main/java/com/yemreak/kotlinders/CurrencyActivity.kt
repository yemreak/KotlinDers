package com.yemreak.kotlinders

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_currency.*
import org.json.JSONObject
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

// İnternet izni gerekli (manifestte)
class CurrencyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)

        bindEvents()
    }

    private fun bindEvents(){
        btn_show.setOnClickListener { btnShowOnClick() }
    }

    private fun btnShowOnClick(){
        try {
            val url = "https://api.fixer.io/latest?base="
            val currency = et_currency.text.toString()

            setTVsDefault()

            Download().execute(url + currency) // vararg params adlı yere url'yi verir.
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setTVsDefault(){
        tv_try.text = "TRY: "
        tv_chf.text = "CHF: "
        tv_czk.text = "CZK: "
    }

    /**
     * Neden :  İnternetten veri indirmek işlemciyi yorar. Bu yüzden bu yapıyı
     * ana sistemle senkronize olmayan şekilde çalıştırıyoruz. Bu sayede uygulama,
     * ben veriyi indirene kadar donmasın, hâlâ çalışsın. Yüksek veri indirmelerinde
     * çok önemlidir.
     *
     *
     * Async : Senkronize olmayan bir işlem yapmak
     * String : Verilecek input
     * Void : Progress bar kullanıcalak mı, 0'dan 100'e giden. Void = Hayır
     * String : Sonuç
     */
    inner class Download : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String { // Arka plan
            var result = "" // Tüm veri buraya yazılacak.
            val url: URL // Bağlantı (API linki)
            val httpURLConnection: HttpURLConnection // Bağlantı tipi, bağlantıyı oluşturmak için.

            try {
                url = URL(params[0]) // vararg alınan input dizisi
                httpURLConnection = url.openConnection() as HttpURLConnection // İnternete bağlanmak için connection
                val inputStream = httpURLConnection.inputStream // Okuma ortamı
                val inputStreamReader = InputStreamReader(inputStream) // Okuyucu

                var data = inputStreamReader.read()

                while (data > 0) { // Okunacak veri kalmadığında, data -1 olur.
                    val character = data.toChar()
                    result += character

                    data = inputStreamReader.read() // bir sonraki döngüye girmemiz için gereken kod
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return result // Olay bittikten sonra, @see onPostExecute çalışır.
        }

        override fun onPostExecute(result: String?) { // İndirdikten sonraki işlemler.
            super.onPostExecute(result)
            // Veriyi işlerken hata alma ihtimaline karşı.
            try {
                val jsonObject = JSONObject(result) // Sonucu alıyoruz.

                /**
                 * Verileri alırken, JSON yapısındaki gibi alıyoruz.
                 * @see btnShowOnClick içimdeki execute(url)'in url'sini webde açarsak, JSON'u görürüz.
                 **/
                val base = jsonObject.getString("base")
                val date = jsonObject.getString("date")
                val rates = jsonObject.getString("rates")

                // Rates bir JSON objesidir.
                val newJSONObject = JSONObject(rates)
                val chf = newJSONObject.getString("CHF")
                val czk = newJSONObject.getString("CZK")

                var tl : String? = null

                if(base.equals("try", true))
                    tl = "1.000"
                else
                    tl = newJSONObject.getString("TRY")

                tv_chf.text = "CHF: " + chf
                tv_czk.text = "CZK " + czk
                tv_try.text = "TRY: " + tl



            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}
