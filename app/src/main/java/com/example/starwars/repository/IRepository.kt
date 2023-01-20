package com.example.starwars.repository

interface IRepository<T> {

    suspend fun getAll(): List<T>

    suspend fun getSingle(uid: Int): T?
}