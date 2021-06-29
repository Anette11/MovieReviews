package com.example.testtaskmoviereview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testtaskmoviereview.databinding.FragmentMovieListBinding

class MovieListFragment : Fragment() {
    private var fragmentMovieListBinding: FragmentMovieListBinding? = null
    private val binding get() = fragmentMovieListBinding!!
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieRecyclerViewAdapter: MovieRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMovieListBinding = FragmentMovieListBinding.inflate(inflater, container, false)
        val view = binding.root

        movieViewModel =
            ViewModelProvider(
                requireActivity(),
                MovieViewModelFactory()
            ).get(MovieViewModel::class.java)

        if (movieViewModel.getMutableLiveDataLoadMoreList().value == null) {
            loadMore()
        }

        setRecyclerViewAdapter()
        setLoadMoreListObserver()
        return view
    }

    private fun setLoadMoreListObserver() {
        movieViewModel.getMutableLiveDataLoadMoreList().observe(viewLifecycleOwner, {
            movieRecyclerViewAdapter.updateList(it)
        })
    }

    private fun setRecyclerViewAdapter() {
        with(binding.recyclerView) {
            val linearLayoutManager = LinearLayoutManager(activity)
            layoutManager = linearLayoutManager
            movieRecyclerViewAdapter = MovieRecyclerViewAdapter()
            adapter = movieRecyclerViewAdapter
            this.adapter!!.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            setOnScrollListenerForRecyclerView()
        }
    }

    private fun setOnScrollListenerForRecyclerView() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                    val arrayList = movieViewModel.getMutableLiveDataLoadMoreList().value
                    val lastCompletelyVisibleItemPosition =
                        linearLayoutManager?.findLastCompletelyVisibleItemPosition()

                    if (linearLayoutManager != null &&
                        arrayList != null &&
                        lastCompletelyVisibleItemPosition == arrayList.size - 1
                    ) {
                        binding.progressBar.visibility = View.VISIBLE
                        loadMore()
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun loadMore() {
        movieViewModel.loadMore()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentMovieListBinding = null
    }
}