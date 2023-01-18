package com.example.starwars.ui.character.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starwars.R
import com.example.starwars.databinding.VehiculeListItemBinding
import com.example.starwars.model.Vehicule

class VehiclesAdapter : ListAdapter<Vehicule, VehiclesAdapter.ViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Vehicule>() {
            override fun areItemsTheSame(oldItem: Vehicule, newItem: Vehicule): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Vehicule, newItem: Vehicule): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            VehiculeListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: VehiculeListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(vehicule: Vehicule) {
            binding.apply {
                vehicleNameTextView.text = vehicule.name
                modelTextView.text = vehicule.model
                maxSpeedTextView.text = root.context.getString(R.string.max_speed, vehicule.maxSpeed)
            }
        }
    }
}