package com.example.starwars.repository

import com.example.starwars.util.StringManager
import com.example.starwars.api.ApiProvider
import com.example.starwars.api.VehiculeAPI
import com.example.starwars.model.Vehicule

class VehiculeRepository {
    private val api : VehiculeAPI = ApiProvider.getInstance().create(VehiculeAPI::class.java)

    suspend fun fetchVehicules(urls : List<String>): List<Vehicule> {
        val vehicules = ArrayList<Vehicule>()
        for(planetURL : String in urls) {
            val id = StringManager.retriveIdFromURL(planetURL)
            val vehicule = api.getVehicule(id).body()
            if (vehicule != null)
                vehicules.add(vehicule)
        }
        return vehicules
    }
}
