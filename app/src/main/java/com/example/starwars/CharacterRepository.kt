package com.example.starwars

import com.example.starwars.api.ApiProvider
import com.example.starwars.api.CharacterAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.starwars.model.Character

class CharacterRepository {

    private val api : CharacterAPI = ApiProvider.getInstance().create(CharacterAPI::class.java)

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

    suspend fun fetchCharacters() = withContext(Dispatchers.IO) {
        api.getCharacters(1).body()?.results
    }
}