package com.example.starwars.repository

import com.example.starwars.api.CharacterAPI
import com.example.starwars.dao.CharacterDao
import com.example.starwars.model.Character
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.ceil

@Singleton
class CharacterRepository @Inject constructor(
    private val dao: CharacterDao,
    private val api: CharacterAPI
) : IRepository<Character> {
    private val characters = mutableListOf<Character>()

    fun getLocalCharacters() = characters

    override suspend fun getAll(): List<Character> = coroutineScope {
        val charactersFromApiCall = async { api.getCharacters(1).body()?.results }
        val charactersFromDaoCall = async { dao.getAll() }
        val (charactersFromApi, charactersFromDao) = awaitAll(charactersFromApiCall, charactersFromDaoCall)
        if (charactersFromApi != charactersFromDao) {
            launch {
                dao.insertAll(charactersFromApi)
            }
        }
        if (charactersFromApi != null)
            characters.addAll(charactersFromApi)
        characters
    }

    override suspend fun getSingle(uid: Int): Character = coroutineScope {
        dao.findById(uid)
    }

    suspend fun fetchCharactersWhileHandlingPager(): List<Character> = coroutineScope {
        val calls = ArrayList<Deferred<List<Character>>>()
        val firstCharactersPage = api.getCharacters(1).body()
        if (firstCharactersPage != null) {
            val numberOfPage = ceil(firstCharactersPage.count / CHARACTER_PER_PAGE)
            val i = AtomicInteger(0)
            while (i.get() < numberOfPage) {
                calls.add(async {
                    i.incrementAndGet()
                    api.getCharacters(i.get()).body()!!.results
                })
            }
        }
        return@coroutineScope calls.awaitAll().flatten()
    }

    companion object {
        private const val CHARACTER_PER_PAGE = 10.0F
    }
}
