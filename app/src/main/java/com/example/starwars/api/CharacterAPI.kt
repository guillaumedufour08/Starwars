package com.example.starwars.api

import retrofit2.Response
import retrofit2.http.GET

interface CharacterAPI {
    @GET("people")
    suspend fun getCharacters() : Response<CharacterList>
}
