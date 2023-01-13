package com.example.starwars.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwars.R
import com.example.starwars.databinding.FragmentFirstBinding
import com.example.starwars.viewmodel.CharactersListViewModel
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val viewModel : CharactersListViewModel by activityViewModels()
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val charactersListAdapter = CharactersListAdapter { character ->
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        viewModel.characterList.observe(viewLifecycleOwner) { characters ->
            lifecycle.coroutineScope.launch {
                charactersListAdapter.submitList(characters)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchCharacters()
        binding.charactersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.charactersRecyclerView.adapter = charactersListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}