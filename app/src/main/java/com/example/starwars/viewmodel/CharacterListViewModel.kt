package com.example.starwars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.repository.CharacterRepository
import com.example.starwars.repository.PlanetRepository
import com.example.starwars.util.StringManager
import com.example.starwars.repository.VehiculeRepository
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

    private val _isInCall = MutableLiveData<Boolean>()
    val isInCall : LiveData<Boolean> =  _isInCall

    private val _selectedCharacter = MutableLiveData<Character?>()
    val selectedCharacter : LiveData<Character?> = _selectedCharacter

    private val _homeworld = MutableLiveData<Planet?>()
    val homeworld : LiveData<Planet?> = _homeworld

    private val _vehiclesList = MutableLiveData<List<Vehicule>?>()
    val vehiclesList : LiveData<List<Vehicule>?> = _vehiclesList

    private val _areVehiclesBeingFetched = MutableLiveData<Boolean>()
    val areVehiclesBeingFetched : LiveData<Boolean> =  _areVehiclesBeingFetched

    fun fetchCharacters() {
        if (_charactersList.value == null) {
            _isInCall.value = true
            viewModelScope.launch {
                _charactersList.value = characterRepository.fetchCharacters()
                _isInCall.value = false
            }
        }
    }

    fun setSelectedCharacter(character: Character) {
        _selectedCharacter.value = character
    }

    fun unselectCharacter() {
        _selectedCharacter.value = null
        _homeworld.value = null
        _vehiclesList.value = null
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
        _areVehiclesBeingFetched.value = true
        viewModelScope.launch {
            _areVehiclesBeingFetched.value = false
            _vehiclesList.value = vehiculeRepository.fetchVehicules(_selectedCharacter.value!!.vehicleURLS)
        }
    }
}