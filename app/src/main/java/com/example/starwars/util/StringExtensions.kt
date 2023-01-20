package com.example.starwars.util

fun String.retrieveIdFromURL(): Int {
    val trimmedURL = this.trimEnd('/')
    val start = trimmedURL.lastIndexOf("/") + 1
    return this.substring(start, trimmedURL.lastIndex + 1).toInt()
}
