package com.example.starwars.repository

interface IRepository<T> {
    suspend fun findAll(): List<T>
    suspend fun findById(uid: Int): T?
}