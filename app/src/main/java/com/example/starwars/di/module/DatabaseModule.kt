package com.example.starwars.di.module

import android.content.Context
import androidx.room.Room
import com.example.starwars.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "swapi")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun providesCharacterDao(db: AppDatabase) = db.characterDao()
}