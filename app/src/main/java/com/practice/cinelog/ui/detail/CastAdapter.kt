package com.practice.cinelog.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practice.cinelog.R
import com.practice.cinelog.databinding.ItemCastBinding
import com.practice.cinelog.domain.model.Cast

class CastAdapter : ListAdapter<Cast, CastAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCastBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemCastBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cast: Cast) {
            binding.tvName.text      = cast.name
            binding.tvCharacter.text = cast.character

            Glide.with(binding.root)
                .load(cast.profileUrl)
                .placeholder(R.color.gray)
                .circleCrop()
                .into(binding.ivProfile)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Cast>() {
        override fun areItemsTheSame(old: Cast, new: Cast) = old.id == new.id
        override fun areContentsTheSame(old: Cast, new: Cast) = old == new
    }
}