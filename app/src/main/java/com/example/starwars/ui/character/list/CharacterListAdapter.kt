package com.example.starwars.ui.character.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.starwars.R
import com.example.starwars.util.StringFinder
import com.example.starwars.databinding.CharacterListItemBinding
import com.example.starwars.databinding.CharacterListStatsItemBinding
import com.example.starwars.model.Character

class CharacterListAdapter(private val onItemClicked: (Character) -> Unit) : RecyclerView.Adapter<CharacterListAdapter.DataAdapterViewHolder>() {

    private val characters = mutableListOf<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapterViewHolder {
        val binding = when (viewType) {
            TYPE_CHARACTER -> CharacterListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            else -> CharacterListStatsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        }
        return DataAdapterViewHolder(binding)
    }


    override fun onBindViewHolder(holder: DataAdapterViewHolder, position: Int) {
        when (position) {
            characters.size -> holder.bindStats()
            else -> holder.bindCharacter(characters[position])
        }
    }

    override fun getItemCount(): Int {
        return when (characters.size) {
            0 -> 0
            else -> characters.size + 1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            characters.size -> TYPE_STATS
            else -> TYPE_CHARACTER
        }
    }

    fun setData(data: List<Character>) {
        characters.apply {
            clear()
            addAll(data)
        }
    }

    companion object {
        private const val TYPE_CHARACTER = 0
        private const val TYPE_STATS = 1
    }

    inner class DataAdapterViewHolder(private val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindCharacter(character: Character) {
            (binding as CharacterListItemBinding).apply {
                characterNameTextView.text = character.name
                heightTextView.text = root.context.getString(R.string.height, character.height)
                genderTextView.text = StringFinder.findFromName(root, character.gender)

                characterListItemCardView.contentDescription = root.context.getString(R.string.character_list_item_button_description, character.name)
                characterListItemCardView.setOnClickListener {
                    onItemClicked(character)
                }
            }
        }

        fun bindStats() {
            (binding as CharacterListStatsItemBinding).apply {
                characterCountTextView.text = characters.size.toString()
                var countHeight = 0
                characters.forEach { character ->
                    countHeight += character.height.toInt()
                }
                val averageHeight = countHeight / characters.size
                characterAverageHeightTextView.text = root.context.getString(R.string.height, averageHeight.toString())
            }
        }
    }
}
