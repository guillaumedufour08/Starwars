package com.example.starwars

import com.example.starwars.api.ApiProvider
import com.example.starwars.api.VehiculeAPI

class VehiculeRepository {
    private val api : VehiculeAPI = ApiProvider.getInstance().create(VehiculeAPI::class.java)
}