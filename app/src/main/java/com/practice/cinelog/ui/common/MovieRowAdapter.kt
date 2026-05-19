package com.practice.cinelog.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practice.cinelog.R
import com.practice.cinelog.databinding.ItemMovieRowBinding
import com.practice.cinelog.domain.model.Movie

class MovieRowAdapter(
    private val onMovieClick: (Movie) -> Unit
) : ListAdapter<Movie, MovieRowAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieRowBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemMovieRowBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.tvTitle.text  = movie.title
            binding.tvYear.text   = movie.releaseYear
            binding.tvRating.text = "⭐ ${movie.formattedString}"

            Glide.with(binding.root)
                .load(movie.posterUrl)
                .placeholder(R.color.gray)
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