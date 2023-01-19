package com.example.starwars.util

import android.util.Log
import java.text.SimpleDateFormat

//fun String.formatDate(): String {
//    var formattedDateString : String = this
//    try {
//        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
//        val formatter = SimpleDateFormat("dd/MM/yyyy")
//        val date = parser.parse(this)
//        if (date != null)
//            formattedDateString = formatter.format(date)
//    } catch (exception: Exception) {
//        Log.e("error parsing date", exception.toString())
//    }
//    return formattedDateString
//}

fun String.retrieveIdFromURL(): String {
    val trimedURL = this.trimEnd('/')
    val start = trimedURL.lastIndexOf("/") + 1
    return this.substring(start, trimedURL.lastIndex + 1)
}