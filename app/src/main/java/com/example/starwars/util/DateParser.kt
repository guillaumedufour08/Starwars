package com.example.starwars.util

import android.util.Log
import java.text.SimpleDateFormat

object DateParser {
    private val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    private val formatter = SimpleDateFormat("dd/MM/yyyy")

    fun format(stringDate: String): String {
        var formattedDateString : String = stringDate
        try {
            val date = parser.parse(stringDate)
            if (date != null)
                formattedDateString = formatter.format(date)
        } catch (exception: Exception) {
            Log.e("error parsing date", exception.toString())
        }
        return formattedDateString
    }
}