package com.example.starwars.model

import com.example.starwars.model.Character
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

@JsonIgnoreProperties(ignoreUnknown = true)
data class CharacterPage(@JsonProperty("next") val nextPageURL: String?,
                         @JsonProperty("results") val results: List<Character>)
