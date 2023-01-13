package com.example.starwars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.CharacterRepository
import com.example.starwars.model.Character
import kotlinx.coroutines.launch

class CharacterListViewModel : ViewModel() {

    private val characterRepository = CharacterRepository()

    private val _charactersList = MutableLiveData<List<Character>>()
    val characterList : LiveData<List<Character>> =  _charactersList

    private val _areCharactersBeingFetched = MutableLiveData<Boolean>()
    val areCharactersBeingFetched : LiveData<Boolean> =  _areCharactersBeingFetched

    fun fetchCharacters() {
        if (_charactersList.value == null) {
            _areCharactersBeingFetched.value = true
            viewModelScope.launch {
                _charactersList.value = characterRepository.fetchCharacters()
                _areCharactersBeingFetched.value = false
            }
        }
    }
}