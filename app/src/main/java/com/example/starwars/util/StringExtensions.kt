package com.example.starwars.util

fun String.retrieveIdFromURL(): String {
    val trimedURL = this.trimEnd('/')
    val start = trimedURL.lastIndexOf("/") + 1
    return this.substring(start, trimedURL.lastIndex + 1)
}