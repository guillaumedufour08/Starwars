package com.example.starwars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.model.Character
import com.example.starwars.model.Planet
import com.example.starwars.model.Starship
import com.example.starwars.repository.PlanetRepository
import com.example.starwars.repository.StarshipRepository
import com.example.starwars.util.retrieveIdFromURL
import kotlinx.coroutines.launch

class CharacterDetailViewModel : ViewModel() {
    private val planetRepository = PlanetRepository()
    private val starshipRepository = StarshipRepository()

    private val _selectedCharacter = MutableLiveData<Character?>()
    val selectedCharacter : LiveData<Character?> = _selectedCharacter

    private val _homeworld = MutableLiveData<Planet>()
    val homeworld : LiveData<Planet> = _homeworld

    private val _starshipList = MutableLiveData<List<Starship>>()
    val starshipList : LiveData<List<Starship>> = _starshipList

    private val _areStarshipsBeingFetched = MutableLiveData<Boolean>()
    val areStarshipsBeingFetched : LiveData<Boolean> =  _areStarshipsBeingFetched

    private val _isHomeworldBeingFetched = MutableLiveData<Boolean>()
    val isHomeworldBeingFetched : LiveData<Boolean> =  _isHomeworldBeingFetched

    fun setSelectedCharacter(character: Character) {
        _selectedCharacter.value = character
    }

    fun fetchCharacterPlanetAndStarships() {
        fetchPlanet()
        fetchStarships()
    }

    private fun fetchPlanet() {
        val id = _selectedCharacter.value!!.homeworldURL.retrieveIdFromURL()
        _isHomeworldBeingFetched.value = true
        viewModelScope.launch {
            _isHomeworldBeingFetched.value = false
            _homeworld.value = planetRepository.fetchPlanet(id)
        }
    }

    private fun fetchStarships() {
        _areStarshipsBeingFetched.value = true
        viewModelScope.launch {
            _areStarshipsBeingFetched.value = false
            _starshipList.value = starshipRepository.fetchStarships(_selectedCharacter.value!!.starshipsURLS)
        }
    }
}