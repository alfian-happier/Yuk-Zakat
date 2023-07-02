package app.alfian.yukzakat.util

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import app.alfian.yukzakat.data.model.ZakatType
import com.google.android.material.textfield.TextInputEditText
import java.io.ByteArrayOutputStream
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Alfian on 6/13/2023.
 */

fun Context.toast(text: String?) {
    Toast.makeText(this, "$text", Toast.LENGTH_SHORT).show()
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun TextInputEditText.addRupiahFormatter() {
    this.addTextChangedListener(object : TextWatcher {
        private var current = ""
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.toString() != current) {
                this@addRupiahFormatter.removeTextChangedListener(this)
                val cleanString = s.toString().replace("\\D".toRegex(), "")
                val parsed = if (cleanString.isBlank()) 0.0 else cleanString.toDouble()

                val localeID = Locale("in","ID")
                val formatted = NumberFormat.getCurrencyInstance(localeID).format(parsed)
                val clean = formatted.substring(0,formatted.indexOf(","))
                current = clean
                this@addRupiahFormatter.setText(clean)
                this@addRupiahFormatter.setSelection(clean.length)

                this@addRupiahFormatter.addTextChangedListener(this)
            }
        }
        override fun afterTextChanged(s: Editable?) {}
    })
}

fun String.isMoreEqualsThan(minimal : String) : Boolean {
    val cleanMinimal = minimal.replace(".","").toDouble()
    val clean = this.replace("Rp","").replace(".","").toDouble()
    return clean >= cleanMinimal
}

fun String?.copyString(context: Context) {
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("text", this)
    clipboardManager.setPrimaryClip(clipData)
    context.toast("Bank number copied.")
}

fun Bitmap.encodeToBase64() : String {
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG,100,baos)
    val b = baos.toByteArray()
    return Base64.encodeToString(b,Base64.DEFAULT)
}

fun ImageView.loadFromBase64(base64String: String) {
    try {
        val decodedString = Base64.decode(base64String, Base64.DEFAULT)
        val bitmap =
            BitmapFactory.decodeByteArray(decodedString,0,decodedString.size)
        setImageBitmap(bitmap)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Any?.isNotNull() : Boolean {
    return this != null
}

fun Activity.goToActivity(activity: Activity) {
    startActivity(Intent(this, activity.javaClass))
}
fun Activity.changeActivity(activity: Activity) {
    startActivity(Intent(this, activity.javaClass))
    finish()
}

fun getZakatTypeByName(name: String) : ZakatType? {
    for(z in ZakatType.values()){
        if (z.name == name){
            return z
        }
    }
    return null
}

fun Date.toStringDate() : String {
    return SimpleDateFormat("dd MMMM yyyy",Locale("id", "ID")).format(this)
}

fun Double.toRupiahFormat() : String {
    val localeID = Locale("in","ID")
    val formatted = NumberFormat.getCurrencyInstance(localeID).format(this )
    return formatted.substring(0,formatted.indexOf(","))
}

fun String.getCleanDoubleOrNull() : Double? {
    val removedRp = this.substring(2,this.length)
    val doubleString = removedRp.replace(".","")
    return doubleString.toDoubleOrNull()
}

fun log(content: String?) {
    Log.v("devvv", "$content")
}