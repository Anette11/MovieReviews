package com.example.testtaskmoviereview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.testtaskmoviereview.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private var fragmentSplashBinding: FragmentSplashBinding? = null
    private val binding get() = fragmentSplashBinding!!
    private lateinit var movieViewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSplashBinding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root
        setAnimationOnSplashImageView()

        movieViewModel = ViewModelProvider(
            requireActivity(),
            MovieViewModelFactory()
        ).get(MovieViewModel::class.java)

        setObserverOnMutableLiveDataMovie(view)
        return view
    }

    private fun setObserverOnMutableLiveDataMovie(view: View) {
        movieViewModel.getMutableLiveDataMovie().observe(viewLifecycleOwner, {
            if (it != null) {
                navigateToFragmentMovieList(view)
            }
        })
    }

    private fun setAnimationOnSplashImageView() {
        val animation = AnimationUtils
            .loadAnimation(requireActivity(), R.anim.rotate_splash_image_view)
        binding.imageViewIconApp.startAnimation(animation)
    }

    private fun navigateToFragmentMovieList(view: View) {
        val navDirection = SplashFragmentDirections
            .actionNavigateFromSplashFragmentToMovieListFragment()
        Navigation.findNavController(view).navigate(navDirection)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentSplashBinding = null
    }
}