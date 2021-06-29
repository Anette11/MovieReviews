package com.example.testtaskmoviereview

class MovieRepository {
    suspend fun getMovie(): Movie = MovieRetrofitInstance
        .getInstance(MovieConstants.BASE_URL)
        .create(MovieWebservice::class.java)
        .getMovie()
}