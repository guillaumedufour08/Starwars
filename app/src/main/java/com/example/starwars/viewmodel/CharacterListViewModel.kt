package com.example.starwars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.CharacterRepository
import com.example.starwars.PlanetRepository
import com.example.starwars.StringManager
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

    private val _vehiclesList = MutableLiveData<List<Vehicule>>()
    val vehiclesList : LiveData<List<Vehicule>> = _vehiclesList

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

    fun unselectCharacter() {

    }

    fun fetchCharacterPlanetAndVehicules() {
        fetchPlanet()
        fetchVehicules()
    }

    private fun fetchPlanet() {
        val id = StringManager.retriveIdFromURL(_selectedCharacter.value!!.homeworldURL)
        viewModelScope.launch {
            _homeworld.value = planetRepository.fetchPlanet(id)
        }
    }

    private fun fetchVehicules() {
        viewModelScope.launch {
            _vehiclesList.value = vehiculeRepository.fetchVehicules(_selectedCharacter.value!!.vehicleURLS)
        }
    }
}