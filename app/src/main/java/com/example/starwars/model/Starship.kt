package com.example.starwars.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Starship(@JsonProperty("name") val name: String,
                    @JsonProperty("model") val model: String,
                    @JsonProperty("manufacturer") val manufacturer: String,
                    @JsonProperty("max_atmosphering_speed") val maxSpeed: String)
