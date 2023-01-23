package com.example.starwars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.model.Character
import com.example.starwars.model.Planet
import com.example.starwars.model.Starship
import com.example.starwars.repository.CharacterRepository
import com.example.starwars.repository.PlanetRepository
import com.example.starwars.repository.StarshipRepository
import com.example.starwars.util.retrieveIdFromURL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val planetRepository: PlanetRepository,
    private val starshipRepository: StarshipRepository
) : ViewModel() {
    private val _character = MutableLiveData<Character?>()
    val character: LiveData<Character?> = _character

    private val _homeworld = MutableLiveData<Planet>()
    val homeworld: LiveData<Planet> = _homeworld

    private val _starshipList = MutableLiveData<List<Starship>>()
    val starshipList : LiveData<List<Starship>> = _starshipList

    private val _isStarshipRepositoryInUse = MutableLiveData<Boolean>()
    val isStarshipRepositoryInUse: LiveData<Boolean> =  _isStarshipRepositoryInUse

    private val _isHomeworldRepositoryInUse = MutableLiveData<Boolean>()
    val isHomeworldRepositoryInUse: LiveData<Boolean> =  _isHomeworldRepositoryInUse

    fun getCharacterWithEntities(uid: Int) {
        viewModelScope.launch {
            _character.value = characterRepository.findById(uid)
        }.invokeOnCompletion {
            fetchPlanet()
            fetchStarships()
        }
    }

    private fun fetchPlanet() {
        _isHomeworldRepositoryInUse.value = true
        viewModelScope.launch {
            val id = _character.value?.homeworldURL?.retrieveIdFromURL()
            if (id != null) {
                _homeworld.value = planetRepository.findById(id)
                _isHomeworldRepositoryInUse.value = false
            }
        }
    }

    private fun fetchStarships() {
        _isStarshipRepositoryInUse.value = true
        viewModelScope.launch {
            _character.value?.let {
                _starshipList.value = starshipRepository.getStarshipsFromUrls(it.starshipsURLS)
            }
            _isStarshipRepositoryInUse.value = false
        }
    }
}
