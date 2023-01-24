package com.example.starwars.repository

import com.example.starwars.api.CharacterAPI
import com.example.starwars.dao.CharacterDao
import com.example.starwars.di.module.coroutine.DefaultDispatcher
import com.example.starwars.model.Character
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.ceil

@Singleton
class CharacterRepository @Inject constructor(
    private val dao: CharacterDao,
    private val api: CharacterAPI,
    @DefaultDispatcher
    private val dispatcher: CoroutineDispatcher
) : IRepository<Character> {
    private val characters = mutableListOf<Character>()

    fun getLocalCharacters() = characters

    override suspend fun findAll(): List<Character> = coroutineScope {
        val charactersFromApiCall = async { api.getCharacters(1).body()?.results }
        val charactersFromDaoCall = async { dao.findAll() }
        val (charactersFromApi, charactersFromDao) = awaitAll(charactersFromApiCall, charactersFromDaoCall)
        if (charactersFromApi != charactersFromDao && charactersFromApi != null) {
            launch { dao.insertAll(charactersFromApi) }
        }
        charactersFromApi?.let { characters.addAll(it) }
        return@coroutineScope characters
    }

    override suspend fun findById(uid: Int): Character = coroutineScope {
        dao.findById(uid)
    }

    suspend fun findAllWithPageHandling(): List<Character> = coroutineScope {
        val calls = ArrayList<Deferred<List<Character>?>>()
        val firstCharactersPage = api.getCharacters(1).body() ?: return@coroutineScope listOf()
        val numberOfPage = ceil(firstCharactersPage.count / CHARACTER_PER_PAGE)
        val i = AtomicInteger(1)
        while (i.get() < numberOfPage) {
            calls.add(async {
                i.incrementAndGet()
                api.getCharacters(i.get()).body()?.results
            })
        }
        characters.addAll(firstCharactersPage.results + calls.awaitAll().filterNotNull().flatten())
        withContext(dispatcher) { dao.insertAll(characters) }
        return@coroutineScope characters
    }

    companion object {
        private const val CHARACTER_PER_PAGE = 10.0F
    }
}
