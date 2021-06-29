package com.example.testtaskmoviereview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val mutableLiveDataMovie: MutableLiveData<Movie> = MutableLiveData()
    private val mutableLiveDataLoadMore: MutableLiveData<ArrayList<Result>> = MutableLiveData()
    private val mutableLiveDataCounter: MutableLiveData<Int> = MutableLiveData()

    fun getMutableLiveDataMovie(): LiveData<Movie> = mutableLiveDataMovie
    fun getMutableLiveDataLoadMoreList(): LiveData<ArrayList<Result>> = mutableLiveDataLoadMore

    fun getMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            mutableLiveDataMovie.postValue(MovieRepository().getMovie())
        }
    }

    fun loadMore() {
        var counter = getCounterAmount()

        val arrayListWhole: ArrayList<Result> =
            mutableLiveDataMovie.value!!.results as ArrayList<Result>

        val arrayListFirstPortion =
            if (mutableLiveDataLoadMore.value == null) {
                ArrayList()
            } else {
                mutableLiveDataLoadMore.value
            }

        for (i in 0..3) {
            if (checkIfWholeListOfMoviesFromServerIsStillLargerThenNewListToLoadMore()) {
                arrayListFirstPortion!!.add(arrayListWhole[counter])
                counter++
                mutableLiveDataCounter.postValue(counter)
            }
        }
        mutableLiveDataLoadMore.postValue(arrayListFirstPortion)
    }

    private fun checkIfWholeListOfMoviesFromServerIsStillLargerThenNewListToLoadMore(): Boolean {
        val size = mutableLiveDataMovie.value!!.results.size
        val counter = getCounterAmount()
        return counter < (size - 1)
    }

    private fun getCounterAmount(): Int {
        return if (mutableLiveDataCounter.value == null) {
            mutableLiveDataCounter.postValue(0)
            0
        } else {
            mutableLiveDataCounter.value!!
        }
    }
}