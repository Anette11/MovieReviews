package com.example.testtaskmoviereview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.testtaskmoviereview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = activityMainBinding.root
        setContentView(view)

        movieViewModel = ViewModelProvider(this, MovieViewModelFactory())
            .get(MovieViewModel::class.java)

        if (movieViewModel.getMutableLiveDataMovie().value == null) {
            movieViewModel.getMovie()
        }
    }
}