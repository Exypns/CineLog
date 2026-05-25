package com.practice.cinelog.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practice.cinelog.R
import com.practice.cinelog.databinding.ItemMovieBinding
import com.practice.cinelog.domain.model.Movie

class MovieAdapter(
    private val onMovieClick: (Movie) -> Unit
) : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MovieViewHolder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.tvTitle.text = movie.title
            binding.tvRating.text = "⭐ ${movie.formattedString}"
            binding.tvRatingBadge.text = movie.formattedString

            Glide.with(binding.root)
                .load(movie.posterUrl)
                .placeholder(R.color.dark_surface)
                .centerCrop()
                .into(binding.ivPoster)

            binding.root.setOnClickListener { onMovieClick(movie) }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(old: Movie, new: Movie) = old.id == new.id
        override fun areContentsTheSame(old: Movie, new: Movie) = old == new
    }
}