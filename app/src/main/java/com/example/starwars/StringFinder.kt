package com.example.starwars

import android.app.Application
import android.view.View

class StringFinder {
    companion object {
        fun findFromName(root: View, name: String): String {
            val packageName = root.context.packageName
            val resourceId = root.resources.getIdentifier(name, "string", packageName)
            return if (resourceId != 0)  root.context.getString(resourceId) else name
        }
    }
}