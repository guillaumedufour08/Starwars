package com.example.starwars.repository

import com.example.starwars.api.PlanetAPI
import com.example.starwars.model.Planet
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlanetRepository @Inject constructor(
    private val api: PlanetAPI
) : IRepository<Planet> {

    override suspend fun getAll(): List<Planet> {
        TODO("Not yet implemented")
    }

    override suspend fun getSingle(uid: Int): Planet? = coroutineScope {
        api.getPlanet(uid).body()
    }
}
