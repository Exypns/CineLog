package com.practice.cinelog.ui.watchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practice.cinelog.R
import com.practice.cinelog.data.local.db.MovieEntity
import com.practice.cinelog.databinding.ItemMovieRowBinding

class WatchlistAdapter(
    private val onMovieClick: (MovieEntity) -> Unit,
    private val onRemoveClick: (MovieEntity) -> Unit
) : ListAdapter<MovieEntity, WatchlistAdapter.ViewHolder>(DiffCallback) {

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

        fun bind(movie: MovieEntity) {
            binding.tvTitle.text  = movie.title
            binding.tvYear.text   = movie.releaseYear
            binding.tvRating.text = "⭐ ${String.format("%.1f", movie.voteAverage)}"

            Glide.with(binding.root)
                .load(movie.posterUrl)
                .placeholder(R.color.gray)
                .centerCrop()
                .into(binding.ivPoster)

            binding.root.setOnClickListener { onMovieClick(movie) }
            binding.root.setOnLongClickListener {
                onRemoveClick(movie)
                true
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(old: MovieEntity, new: MovieEntity) = old.id == new.id
        override fun areContentsTheSame(old: MovieEntity, new: MovieEntity) = old == new
    }
}