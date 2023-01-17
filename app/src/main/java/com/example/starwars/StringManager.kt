package com.example.starwars

import android.view.View

class StringManager {
    companion object {
        fun findFromName(root: View, name: String): String {
            val packageName = root.context.packageName
            val resourceId = root.resources.getIdentifier(name, "string", packageName)
            return if (resourceId != 0)  root.context.getString(resourceId) else name
        }

        fun retriveIdFromURL(url: String) : String {
            val trimedURL = url.trimEnd('/')
            val start = trimedURL.lastIndexOf("/") + 1
            return url.substring(start, trimedURL.lastIndex + 1)
        }
    }
}