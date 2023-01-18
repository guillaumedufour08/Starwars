package com.example.starwars.repository

import com.example.starwars.api.ApiProvider
import com.example.starwars.api.StarshipAPI
import com.example.starwars.model.Starship
import com.example.starwars.util.retrieveIdFromURL

class StarshipRepository {
    private val api : StarshipAPI = ApiProvider.getInstance().create(StarshipAPI::class.java)

    suspend fun fetchStarships(urls : List<String>): List<Starship> {
        val starships = ArrayList<Starship>()
        for(starshipURL : String in urls) {
            val starship = api.getStarship(starshipURL.retrieveIdFromURL()).body()
            if (starship != null)
                starships.add(starship)
        }
        return starships
    }
}
