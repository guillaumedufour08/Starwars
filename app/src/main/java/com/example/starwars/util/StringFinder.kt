package com.example.starwars.util

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object StringFinder {
    fun findFromName(root: View, name: String): String {
        val packageName = root.context.packageName
        val resourceId = root.resources.getIdentifier(name, "string", packageName)
        return if (resourceId != 0)  root.context.getString(resourceId) else name
    }

    // TODO : Fonctionne seulement avec API 26... trouver une solution pour être
    //  compatible avec API 21 (minimum actuel du projet)
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(dateString: String): String {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val dateTime: OffsetDateTime = OffsetDateTime.parse(dateString)
        return dateTime.format(formatter)
    }
}
