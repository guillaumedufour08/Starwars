package com.example.starwars.repository

import com.example.starwars.api.ApiProvider
import com.example.starwars.api.PlanetAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlanetRepository {
    private val api : PlanetAPI = ApiProvider.getInstance().create(PlanetAPI::class.java)

    suspend fun fetchPlanet(id: String) = withContext(Dispatchers.IO) {
        api.getPlanet(id).body()
    }
}