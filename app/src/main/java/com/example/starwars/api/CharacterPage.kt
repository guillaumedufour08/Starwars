package com.example.starwars.api

import com.example.starwars.model.Character
import com.google.gson.annotations.SerializedName

data class CharacterPage(@SerializedName("next") val nextPageURL: String?,
                         val results: List<Character>)
