package com.example.starwars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.model.Character
import com.example.starwars.model.Planet
import com.example.starwars.model.Vehicule
import com.example.starwars.repository.PlanetRepository
import com.example.starwars.repository.VehiculeRepository
import com.example.starwars.util.retrieveIdFromURL
import kotlinx.coroutines.launch

class CharacterDetailViewModel : ViewModel() {
    private val planetRepository = PlanetRepository()
    private val vehiculeRepository = VehiculeRepository()

    private val _selectedCharacter = MutableLiveData<Character?>()
    val selectedCharacter : LiveData<Character?> = _selectedCharacter

    private val _homeworld = MutableLiveData<Planet>()
    val homeworld : LiveData<Planet> = _homeworld

    private val _vehiclesList = MutableLiveData<List<Vehicule>>()
    val vehiclesList : LiveData<List<Vehicule>> = _vehiclesList

    private val _areVehiclesBeingFetched = MutableLiveData<Boolean>()
    val areVehiclesBeingFetched : LiveData<Boolean> =  _areVehiclesBeingFetched

    private val _isHomeworldBeingFetched = MutableLiveData<Boolean>()
    val isHomeworldBeingFetched : LiveData<Boolean> =  _isHomeworldBeingFetched

    fun setSelectedCharacter(character: Character) {
        _selectedCharacter.value = character
    }

    fun fetchCharacterPlanetAndVehicules() {
        fetchPlanet()
        fetchVehicules()
    }

    private fun fetchPlanet() {
        val id = _selectedCharacter.value!!.homeworldURL.retrieveIdFromURL()
        _isHomeworldBeingFetched.value = true
        viewModelScope.launch {
            _isHomeworldBeingFetched.value = false
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