package com.example.testtaskmoviereview

import retrofit2.http.GET

interface MovieWebservice {
    @GET("all.json?api-key=${MovieConstants.API_KEY}")
    suspend fun getMovie(): Movie
}