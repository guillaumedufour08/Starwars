package com.example.starwars.repository

import com.example.starwars.api.StarshipAPI
import com.example.starwars.model.Starship
import com.example.starwars.util.retrieveIdFromURL
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StarshipRepository @Inject constructor(
    private val api: StarshipAPI
) : IRepository<Starship> {
    suspend fun getStarshipsFromUrls(urls: List<String>): List<Starship> = coroutineScope {
        val calls = ArrayList<Deferred<Starship?>>()
        for (starshipURL : String in urls) {
            val call = async {
                api.getStarship(starshipURL.retrieveIdFromURL()).body()
            }
            calls.add(call)
        }
        return@coroutineScope calls.awaitAll().filterNotNull()
    }

    override suspend fun findAll(): List<Starship> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(uid: Int): Starship? {
        TODO("Not yet implemented")
    }
}
