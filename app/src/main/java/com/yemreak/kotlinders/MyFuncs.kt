package com.yemreak.kotlinders

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteAbortException
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.widget.Toast
import java.io.ByteArrayOutputStream


/**
 * Created by Yunus Emre on 1/12/2018.
 */
interface MyFuncs {

    /**
     * Toast mesaj gösterme
     * @param context Aktivite kaynağı
     * @param resId Metin kaynağı numarası
     */
    fun showMessage(context: Context, resId: Int): Toast {
        val toast: Toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT)
        toast.show()
        return toast
    }

    /**
     * Toast mesaj gösterme
     * @param context Aktivite kaynağı
     * @param text Metin
     */
    fun showMessage(context: Context, text: String?): Toast {
        val toast: Toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.show()
        return toast
    }

    /**
     * Mesaj gösterek aktivite başlatma
     * @param context Aktivite kaynağı (this)
     * @param cls Açılacak aktivite (::class.java)
     * @param resId Kaynak kodu (R.id...)
     */
    fun startAct(context: Context, cls: Class<*>, resId: Int) {
        context.startActivity(Intent(context, cls))
        showMessage(context, resId)
    }

    /**
     * Database oluşturma
     * @param dbName Database adı
     * @param context Kaynak (this)
     */
    @Throws(SQLiteAbortException::class)
    fun createDatabase(dbName: String, context: Context): SQLiteDatabase? {
        if (!dbName.equals(""))
            return context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null)
        return null
    }

    /**
     * Tablo oluşturma
     * @param db Table oluşturulacak database
     * @param tableName Oluşturalacak table adı
     * @param vars Table'nın değişken isimleri ver türleri ("name VARCHAR, image BLOB")
     */
    @Throws(SQLiteAbortException::class)
    fun createTable(db: SQLiteDatabase, tableName: String, vars : String) {
        if (!isNull(tableName)) {
            db.execSQL("CREATE TABLE IF NOT EXISTS $tableName ($vars) ")
        }
    }

    fun isNull(str : String) : Boolean{
        return str.equals("")
    }

    fun isNull(arr_situation : Array<String>) : Boolean{
        return arr_situation.any { it.equals("") }
    }
}