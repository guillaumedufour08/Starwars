package com.example.starwars.api

import com.example.starwars.model.Planet
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface VehiculeAPI {
    @GET("vehicules/{id}")
    suspend fun getVehicule(@Path("id") id: String): Response<Planet>
}
