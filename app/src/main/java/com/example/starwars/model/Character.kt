package com.example.starwars.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.starwars.util.retrieveIdFromURL
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
data class Character(@PrimaryKey @JsonProperty("uid") var uid: Int,
                     @JsonProperty("name") val name: String,
                     @JsonProperty("gender") val gender: String,
                     @JsonProperty("height") val height: String,
                     @ColumnInfo(name = "homeworld_url") @JsonProperty("homeworld") val homeworldURL: String,
                     @ColumnInfo(name = "starships_urls") @JsonProperty("starships") val starshipsURLS: List<String>,
                     @JsonProperty("url") val url: String,
                     @JsonProperty("edited") var edited: String) {

    init {
        uid = url.retrieveIdFromURL()
    }
}
