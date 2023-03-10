package com.example.starwars.ui.character.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.starwars.R
import com.example.starwars.util.StringFinder
import com.example.starwars.databinding.CharacterListItemBinding
import com.example.starwars.model.Character

class CharacterListAdapterOld(private val onItemClicked: (Character) -> Unit) : ListAdapter<Character, CharacterListAdapterOld.ViewHolder>(
    DiffCallback
) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(
            CharacterListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: CharacterListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.apply {
                characterNameTextView.text = character.name
                characterListItemCardView.contentDescription = root.context.getString(R.string.character_list_item_button_description, character.name)
                heightTextView.text = root.context.getString(R.string.height, character.height)
                genderTextView.text = StringFinder.findFromName(root, character.gender)
            }
        }
    }
}
