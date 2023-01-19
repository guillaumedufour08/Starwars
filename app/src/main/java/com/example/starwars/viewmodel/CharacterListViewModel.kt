package com.example.starwars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.repository.CharacterRepository
import com.example.starwars.model.Character
import kotlinx.coroutines.launch

class CharacterListViewModel : ViewModel() {

    private val characterRepository = CharacterRepository

    private val _charactersList = MutableLiveData<List<Character>>()
    val characterList : LiveData<List<Character>> =  _charactersList

    private val _isApiBeingCalled = MutableLiveData<Boolean>()
    val isApiBeingCalled : LiveData<Boolean> =  _isApiBeingCalled

    fun fetchCharacters() {
        val savedCharacters = characterRepository.getFetchedCharacters()
        if (savedCharacters.isEmpty()) {
            _isApiBeingCalled.value = true
            viewModelScope.launch {
                _charactersList.value = characterRepository.fetchCharacters()
                _isApiBeingCalled.value = false
            }
        }
    }
}