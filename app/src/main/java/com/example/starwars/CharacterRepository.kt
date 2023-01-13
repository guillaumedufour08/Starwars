package com.example.starwars

import com.example.starwars.api.ApiProvider
import com.example.starwars.api.CharacterAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharacterRepository {

    private val api : CharacterAPI = ApiProvider.getInstance().create(CharacterAPI::class.java)

    suspend fun fetchCharacters() = withContext(Dispatchers.IO) {
        api.getCharacters().body()?.results
    }
}