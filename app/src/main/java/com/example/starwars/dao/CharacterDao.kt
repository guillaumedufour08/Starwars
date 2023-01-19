package com.example.starwars.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.starwars.model.Character

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character")
    suspend fun getAll(): List<Character>

    @Query("SELECT * FROM character WHERE uid = :uid")
    suspend fun findById(uid: Int): Character

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<Character>)
}