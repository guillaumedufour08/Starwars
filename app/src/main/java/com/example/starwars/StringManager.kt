package com.example.starwars

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class StringManager {
    companion object {
        fun findFromName(root: View, name: String): String {
            val packageName = root.context.packageName
            val resourceId = root.resources.getIdentifier(name, "string", packageName)
            return if (resourceId != 0)  root.context.getString(resourceId) else name
        }

        fun retriveIdFromURL(url: String): String {
            val trimedURL = url.trimEnd('/')
            val start = trimedURL.lastIndexOf("/") + 1
            return url.substring(start, trimedURL.lastIndex + 1)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun formatDate(dateString: String): String {
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val dateTime: OffsetDateTime = OffsetDateTime.parse(dateString)
            return dateTime.format(formatter)
        }
    }
}