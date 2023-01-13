package com.example.starwars.api

import com.example.starwars.model.Character

data class CharacterList(val count: Int, val results: List<Character>)
