package com.example.starwars.model

import com.google.gson.annotations.SerializedName

class Character(val name: String,
                val gender: String,
                val height: String,
                @SerializedName("homeworld")
                val homeworldURL: String,
                @SerializedName("starships")
                val starshipsURLS: List<String>,
                val url: String,
                var edited: String): java.io.Serializable