package com.example.starwars.repository

import com.example.starwars.api.ApiProvider
import com.example.starwars.api.CharacterAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.starwars.model.Character

object CharacterRepository {

    private val api : CharacterAPI = ApiProvider.getInstance().create(CharacterAPI::class.java)
    private val characters = mutableListOf<Character>()

//    suspend fun fetchCharactersWhileHandlingPager(): List<Character> {
//        val characters = arrayListOf<Character>()
//        val firstCharactersPage = api.getCharacters(1).body()
//        if (firstCharactersPage != null) {
//            characters.addAll(firstCharactersPage.results)
//            var currentPage = firstCharactersPage
//            var i = 1
//            while (currentPage?.nextPageURL != null) {
//                currentPage = api.getCharacters(i).body()
//                if (currentPage != null) {
//                    characters.addAll(currentPage.results)
//                }
//                ++i
//            }
//        }
//        return characters
//    }

    fun getFetchedCharacters() = characters

    suspend fun fetchCharacters() = withContext(Dispatchers.IO) {
        val fetchedCharacters = api.getCharacters(1).body()?.results
        if (characters.isEmpty() && fetchedCharacters != null)
            characters.addAll(fetchedCharacters)
        characters
    }
}