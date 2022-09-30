package com.example.tp3thiago.ui.api

import com.example.tp3thiago.ui.model.FrutaResponse
import com.example.tp3thiago.ui.model.FrutasDto
import com.example.tp3thiago.ui.model.frutas
import retrofit2.http.GET

interface FrutasService {

    @GET("/api/fruit/all")
    suspend fun getFrutas() : List<frutas>

}