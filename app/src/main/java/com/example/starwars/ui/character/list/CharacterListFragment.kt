package com.example.starwars.ui.character.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwars.R
import com.example.starwars.databinding.FragmentCharacterListBinding
import com.example.starwars.viewmodel.CharacterListViewModel
import com.example.starwars.model.Character

class CharacterListFragment : Fragment() {

    private val viewModel : CharacterListViewModel by activityViewModels()
    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val charactersListAdapterOld = CharacterListAdapterOld(
        onItemClicked = { character ->
            navigateToCharacterDetail(character)
        }
    )

    private val characterListAdapter = CharacterListAdapter(
        onItemClicked = { character ->
            navigateToCharacterDetail(character)
        }
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        initializeListObserver()
        initializeFetchingCharactersObserver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchCharacters()
        initializeCharacterRecyclerView()
    }

    private fun initializeListObserver() {
        viewModel.characterList.observe(viewLifecycleOwner) { characters ->
            //charactersListAdapter.submitList(characters)
            characterListAdapter.setData(characters)
        }
    }

    private fun initializeFetchingCharactersObserver() {
        viewModel.isCallingApi.observe(viewLifecycleOwner) { isCallingApi ->
            binding.charactersLoadingProgressBar.apply {
                visibility = if (isCallingApi) View.VISIBLE else View.GONE
            }
        }
    }

    private fun initializeCharacterRecyclerView() {
        binding.charactersRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = characterListAdapter
        }
    }

    private fun navigateToCharacterDetail(character: Character) {
//        viewModel.setSelectedCharacter(character)
        val bundle = bundleOf("character" to character)
        findNavController().navigate(R.id.action_CharacterListFragment_to_CharacterDetailFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
