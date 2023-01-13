package com.example.starwars.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwars.R
import com.example.starwars.databinding.FragmentCharacterListBinding
import com.example.starwars.viewmodel.CharacterListViewModel
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CharacterListFragment : Fragment() {

    private val viewModel : CharacterListViewModel by activityViewModels()
    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val charactersListAdapter = CharacterListAdapter { character ->
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        initializeListObserver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchCharacters()
        binding.charactersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.charactersRecyclerView.adapter = charactersListAdapter
    }

    private fun initializeListObserver() {
        viewModel.characterList.observe(viewLifecycleOwner) { characters ->
            lifecycle.coroutineScope.launch {
                charactersListAdapter.submitList(characters)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}