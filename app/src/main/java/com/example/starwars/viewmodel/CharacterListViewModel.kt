package com.example.starwars.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.repository.CharacterRepository
import com.example.starwars.model.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
)  : ViewModel() {

    private val _charactersList = MutableLiveData<List<Character>>()
    val characterList : LiveData<List<Character>> =  _charactersList

    private val _isApiBeingCalled = MutableLiveData<Boolean>()
    val isApiBeingCalled : LiveData<Boolean> =  _isApiBeingCalled

    fun fetchCharacters() {
        val localCharacters = characterRepository.getLocalCharacters()
        if (localCharacters.isEmpty()) {
            _isApiBeingCalled.value = true
            viewModelScope.launch {
                _charactersList.value = characterRepository.getCharacters()
                _isApiBeingCalled.value = false
            }
        }
    }
}