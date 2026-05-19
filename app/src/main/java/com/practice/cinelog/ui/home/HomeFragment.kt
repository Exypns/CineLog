package com.practice.cinelog.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.cinelog.R
import com.practice.cinelog.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private val trendingAdapter   = MovieAdapter { movie -> navigateToDetail(movie.id) }
    private val popularAdapter    = MovieAdapter { movie -> navigateToDetail(movie.id) }
    private val nowPlayingAdapter = MovieAdapter { movie -> navigateToDetail(movie.id) }
    private val upcomingAdapter   = MovieAdapter { movie -> navigateToDetail(movie.id) }
    private val topRatedAdapter   = MovieAdapter { movie -> navigateToDetail(movie.id) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        observeUiState()
        setupListeners()
    }

    private fun setupRecyclerViews() {
        binding.rvTrending.setup(trendingAdapter)
        binding.rvPopular.setup(popularAdapter)
        binding.rvNowPlaying.setup(nowPlayingAdapter)
        binding.rvUpcoming.setup(upcomingAdapter)
        binding.rvTopRated.setup(topRatedAdapter)
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.swipeRefresh.isRefreshing = state.isLoading

                    trendingAdapter.submitList(state.trending)
                    popularAdapter.submitList(state.popular)
                    nowPlayingAdapter.submitList(state.nowPlaying)
                    upcomingAdapter.submitList(state.upcoming)
                    topRatedAdapter.submitList(state.topRated)
                }
            }
        }
    }

    private fun setupListeners() {
        binding.swipeRefresh.setOnRefreshListener { viewModel.refresh() }
    }

    private fun navigateToDetail(movieId: Int) {
        // Uncomment after setting up nav graph
//         findNavController().navigate(HomeFragmentDirections.actionHomeToDetail(movieId))
    }

    private fun androidx.recyclerview.widget.RecyclerView.setup(adapter: MovieAdapter) {
        this.adapter = adapter
        this.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}