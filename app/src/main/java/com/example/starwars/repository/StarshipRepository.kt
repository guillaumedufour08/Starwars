package com.example.starwars.repository

import com.example.starwars.api.StarshipAPI
import com.example.starwars.model.Starship
import com.example.starwars.util.retrieveIdFromURL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StarshipRepository @Inject constructor(
    private val api: StarshipAPI
) : IRepository<Starship> {
    suspend fun getStarshipsFromUrls(urls: List<String>): List<Starship> {
        val starships = ArrayList<Starship>()
        for (starshipURL : String in urls) {
            val starship = api.getStarship(starshipURL.retrieveIdFromURL()).body()
            if (starship != null)
                starships.add(starship)
        }
        return starships
    }

    override suspend fun getAll(): List<Starship> {
        TODO("Not yet implemented")
    }

    override suspend fun getSingle(uid: Int): Starship? {
        TODO("Not yet implemented")
    }
}
