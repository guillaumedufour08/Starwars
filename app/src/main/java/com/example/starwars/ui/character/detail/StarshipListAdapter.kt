package com.example.starwars.ui.character.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starwars.R
import com.example.starwars.databinding.StarshipListItemBinding
import com.example.starwars.model.Starship

class StarshipListAdapter : ListAdapter<Starship, StarshipListAdapter.ViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object: DiffUtil.ItemCallback<Starship>() {
            override fun areItemsTheSame(oldItem: Starship, newItem: Starship): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Starship, newItem: Starship): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            StarshipListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: StarshipListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(starship: Starship) {
            binding.apply {
                starshipNameTextView.text = starship.name
                modelTextView.text = starship.model
                maxSpeedTextView.text = root.context.getString(R.string.max_speed, starship.maxSpeed)
            }
        }
    }
}
