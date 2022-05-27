package id.novian.challengechapter7.helper

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*


fun String.toDate(): String? {
    if (this.isEmpty()) {
        return "-"
    }
    val inputPattern = "yyyy-MM-dd"

    val outputPattern = "MMM dd, yyyy"

    val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
    val outputFormat = SimpleDateFormat(outputPattern, Locale("in"))

    val inputDate = inputFormat.parse(this)

    return inputDate?.let {
        outputFormat.format(it)
    }
}

fun String.toDateDetail(): String? {
    if (this.isEmpty()) {
        return "-"
    }
    val inputPattern = "yyyy-MM-dd"

    val outputPattern = "MM/dd/yyyy"

    val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
    val outputFormat = SimpleDateFormat(outputPattern, Locale("in"))

    val inputDate = inputFormat.parse(this)

    return inputDate?.let {
        outputFormat.format(it)
    }
}

fun Bitmap.bitmapToString(): String? {
    val byteArrayOutputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

fun String.stringToBitmap(): Bitmap? {
    return try {
        val encodeByte =
            Base64.decode(this, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    } catch (e: Exception) {
        e.message
        null
    }
}
