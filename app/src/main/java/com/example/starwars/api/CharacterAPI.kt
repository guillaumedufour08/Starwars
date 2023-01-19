package com.example.starwars.api

import com.example.starwars.model.CharacterPage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterAPI {
    @GET("people/")
    suspend fun getCharacters(@Query("page") page: Int) : Response<CharacterPage>
}
