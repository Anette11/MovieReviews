package com.example.testtaskmoviereview

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRetrofitInstance {

    companion object {
        fun getInstance(baseUrl: String): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}