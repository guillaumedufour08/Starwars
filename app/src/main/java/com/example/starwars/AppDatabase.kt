package com.example.starwars

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.starwars.dao.CharacterDao
import com.example.starwars.model.Character

@Database(entities = [Character::class], version = 4, exportSchema = false)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
