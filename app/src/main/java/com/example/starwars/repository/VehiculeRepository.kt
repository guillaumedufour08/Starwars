package com.example.starwars.repository

import com.example.starwars.api.ApiProvider
import com.example.starwars.api.VehiculeAPI
import com.example.starwars.model.Vehicule
import com.example.starwars.util.retrieveIdFromURL

class VehiculeRepository {
    private val api : VehiculeAPI = ApiProvider.getInstance().create(VehiculeAPI::class.java)

    suspend fun fetchVehicules(urls : List<String>): List<Vehicule> {
        val vehicules = ArrayList<Vehicule>()
        for(planetURL : String in urls) {
            val vehicule = api.getVehicule(planetURL.retrieveIdFromURL()).body()
            if (vehicule != null)
                vehicules.add(vehicule)
        }
        return vehicules
    }
}
