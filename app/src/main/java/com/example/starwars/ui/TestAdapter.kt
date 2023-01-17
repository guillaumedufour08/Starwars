package com.example.starwars.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.starwars.R
import com.example.starwars.StringManager
import com.example.starwars.databinding.CharacterListItemBinding
import com.example.starwars.databinding.CharacterListStatsItemBinding
import com.example.starwars.model.Character

class DataAdapter(private val onItemClicked: (Character) -> Unit) : RecyclerView.Adapter<DataAdapter.DataAdapterViewHolder>() {

    private val adapterData = mutableListOf<Character>()

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
            adapterData.size -> holder.bindStats()
            else -> holder.bindCharacter(adapterData[position])
        }
    }

    override fun getItemCount(): Int {
        return when (adapterData.size) {
            0 -> 0
            else -> adapterData.size + 1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            adapterData.size -> TYPE_STATS
            else -> TYPE_CHARACTER
        }
    }

    fun setData(data: List<Character>) {
        adapterData.apply {
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
                genderTextView.text = StringManager.findFromName(root, character.gender)

                characterListItemCardView.contentDescription = root.context.getString(R.string.character_list_item_button_description, character.name)
                characterListItemCardView.setOnClickListener {
                    onItemClicked(character)
                }
            }
        }

        fun bindStats() {
            (binding as CharacterListStatsItemBinding).apply {
                characterCountTextView.text = adapterData.size.toString()
                var countHeight = 0
                adapterData.forEach { character ->
                    countHeight += character.height.toInt()
                }
                val averageHeight = countHeight / adapterData.size
                characterAverageHeightTextView.text = root.context.getString(R.string.height, averageHeight.toString())
            }
        }
    }
}