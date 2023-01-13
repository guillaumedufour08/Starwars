package com.example.starwars.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiProvider {
    private val BASE_URL: String = "https://swapi.dev/api/"

    fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}