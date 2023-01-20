package com.example.starwars.api

import com.example.starwars.model.Starship
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StarshipAPI {
    @GET("starships/{id}")
    suspend fun getStarship(@Path("id") id: Int): Response<Starship>
}
