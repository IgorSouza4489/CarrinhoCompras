package com.example.tp3thiago.ui.api

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {
        private var retrofit = Retrofit
            .Builder()
            .baseUrl("https://www.fruityvice.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun frutasService() =
            retrofit.create(FrutasService::class.java)
    }

}