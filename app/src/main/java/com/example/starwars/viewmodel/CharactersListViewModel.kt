package com.example.starwars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.CharacterRepository
import com.example.starwars.model.Character
import kotlinx.coroutines.launch

class CharactersListViewModel : ViewModel() {

    private val characterRepository = CharacterRepository()

    private val _charactersList = MutableLiveData<List<Character>>()
    val characterList : LiveData<List<Character>> =  _charactersList

    fun fetchCharacters() {
        viewModelScope.launch {
            _charactersList.value = characterRepository.fetchCharacters()
        }
    }
}