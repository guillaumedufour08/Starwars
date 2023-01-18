package com.example.starwars.model

import com.google.gson.annotations.SerializedName

data class Vehicule(val name: String,
                    val model: String,
                    val manufacturer: String,
                    @SerializedName("max_atmosphering_speed")
                    val maxSpeed: String)
