package com.example.starwars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.CharacterRepository
import com.example.starwars.PlanetRepository
import com.example.starwars.VehiculeRepository
import com.example.starwars.model.Character
import com.example.starwars.model.Planet
import com.example.starwars.model.Vehicule
import kotlinx.coroutines.launch

class CharacterListViewModel : ViewModel() {

    private val characterRepository = CharacterRepository()
    private val planetRepository = PlanetRepository()
    private val vehiculeRepository = VehiculeRepository()

    private val _charactersList = MutableLiveData<List<Character>>()
    val characterList : LiveData<List<Character>> =  _charactersList

    private val isInCall = MutableLiveData<Boolean>()
    val areCharactersBeingFetched : LiveData<Boolean> =  isInCall

    private val _selectedCharacter = MutableLiveData<Character>()
    val selectedCharacter : LiveData<Character> = _selectedCharacter

    private val _homeworld = MutableLiveData<Planet>()
    val homeworld : LiveData<Planet> = _homeworld

    private val _vehiculeList = MutableLiveData<List<Vehicule>>()
    val vehiculeList : LiveData<List<Vehicule>> = _vehiculeList

    fun fetchCharacters() {
        if (_charactersList.value == null) {
            isInCall.value = true
            viewModelScope.launch {
                _charactersList.value = characterRepository.fetchCharacters()
                isInCall.value = false
            }
        }
    }

    fun setSelectedCharacter(character: Character) {
        _selectedCharacter.value = character
    }

    fun fetchCharacterPlanetAndVehicules() {
        fetchPlanet()

//        viewModelScope.launch {
//            _vehiculeList.value = vehiculeRepository.fetchVehicules(_selectedCharacter.value!!.vehicleURLS)
//        }
    }

    private fun fetchPlanet() {
        val homeworldURL = _selectedCharacter.value!!.homeworldURL
        val id = homeworldURL[homeworldURL.lastIndex - 1].toString()
        viewModelScope.launch {
            _homeworld.value = planetRepository.fetchPlanet(id)
        }
    }

    private fun fetchVehicules() {

    }
}