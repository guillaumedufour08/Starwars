package com.example.starwars.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Planet(@JsonProperty("name") val name: String,
                  @JsonProperty("climate") val climate: String,
                  @JsonProperty("terrain") val terrain: String,
                  @JsonProperty("population") val population: String)
