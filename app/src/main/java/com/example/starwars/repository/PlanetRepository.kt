package com.example.starwars.repository

import com.example.starwars.api.PlanetAPI
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlanetRepository @Inject constructor(
    private val api: PlanetAPI
) {
    suspend fun fetchPlanet(id: String) = coroutineScope {
        api.getPlanet(id).body()
    }
}
