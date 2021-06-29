package com.example.testtaskmoviereview

data class Movie(
    val results: List<Result>
)

data class Result(
    val display_title: String,
    val summary_short: String,
    val multimedia: Multimedia
)

data class Multimedia(
    val src: String
)