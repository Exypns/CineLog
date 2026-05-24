package com.practice.cinelog.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.practice.cinelog.R
import com.practice.cinelog.databinding.FragmentDetailBinding
import com.practice.cinelog.domain.model.MovieDetail
import com.practice.cinelog.ui.home.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    private val castAdapter    = CastAdapter()
    private val similarAdapter = MovieAdapter { movie ->
        viewModel.loadMovie(movie.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        viewModel.loadMovie(args.movieId)
        observeUiState()
    }

    private fun setupRecyclerViews() {
        binding.rvCast.apply {
            adapter = castAdapter
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
            )
        }
        binding.rvSimilar.apply {
            adapter = similarAdapter
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false
            )
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    state.movie?.let { bindMovie(it) }

                    // Watchlist button
                    binding.btnWatchlist.text =
                        if (state.isInWatchlist) "✓ Watchlist" else "+ Watchlist"

                    // Favorite button
                    binding.btnFavorite.text =
                        if (state.isInFavorites) "♥ Favorited" else "♡ Favorite"
                }
            }
        }
    }

    private fun bindMovie(movie: MovieDetail) {
        binding.tvTitle.text       = movie.title
        binding.tvTagline.text     = movie.tagline
        binding.tvOverview.text    = movie.overview
        binding.tvRating.text      = "⭐ ${movie.formattedString} (${movie.voteCount} votes)"
        binding.tvRuntime.text     = "🕐 ${movie.formattedRuntime}"
        binding.tvReleaseDate.text = "📅 ${movie.releaseDate}"
        binding.tvGenres.text      = movie.genres.joinToString(" • ") { it.name }

        Glide.with(this).load(movie.posterUrl).centerCrop().into(binding.ivPoster)
        Glide.with(this).load(movie.backdropUrl).centerCrop().into(binding.ivBackdrop)

        castAdapter.submitList(movie.cast)
        similarAdapter.submitList(movie.similarMovies)

        // Trailer button
        if (movie.trailerKey != null) {
            binding.btnTrailer.visibility = View.VISIBLE
            binding.btnTrailer.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/watch?v=${movie.trailerKey}"))
                startActivity(intent)
            }
        }

        binding.btnWatchlist.setOnClickListener { viewModel.toggleWatchlist() }
        binding.btnFavorite.setOnClickListener  { viewModel.toggleFavorite() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}