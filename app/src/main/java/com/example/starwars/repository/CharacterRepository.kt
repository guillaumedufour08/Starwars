package com.example.starwars.repository

import com.example.starwars.api.CharacterAPI
import com.example.starwars.dao.CharacterDao
import com.example.starwars.model.Character
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(
    private val dao: CharacterDao,
    private val api: CharacterAPI
){
    private val characters = mutableListOf<Character>()

    fun getFetchedCharacters() = characters

    suspend fun getCharacter(uid: Int): Character = coroutineScope {
        dao.findById(uid)
    }

    suspend fun getCharacters() = coroutineScope {
        val charactersFromApiCall = async { api.getCharacters(1).body()?.results }
        val charactersFromDaoCall = async { dao.getAll() }
        val (charactersFromApi, charactersFromDao) = awaitAll(charactersFromApiCall, charactersFromDaoCall)
        if (charactersFromApi != charactersFromDao) {
            launch {
                if (charactersFromApi != null)
                    dao.insertAll(charactersFromApi)
            }
        }
        charactersFromApi
    }

//    suspend fun fetchCharacters() = withContext(Dispatchers.IO) {
//        val fetchedCharacters = api.getCharacters(1).body()?.results
//        if (characters.isEmpty() && fetchedCharacters != null)
//            characters.addAll(fetchedCharacters)
//        characters
//    }
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
}
