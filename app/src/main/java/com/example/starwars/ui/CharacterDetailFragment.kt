package com.example.starwars.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.starwars.R
import com.example.starwars.databinding.FragmentCharacterDetailBinding
import com.example.starwars.viewmodel.CharacterListViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CharacterDetailFragment : Fragment() {

    private val viewModel : CharacterListViewModel by activityViewModels()
    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchCharacterPlanetAndVehicules()
        initializeCharacterObserver()
        initializePlanetObserver()
    }

    private fun initializeCharacterObserver() {
        viewModel.selectedCharacter.observe(viewLifecycleOwner) { character ->
            binding.apply {
                characterNameTextView.text = character.name
            }
        }
    }

    private fun initializePlanetObserver() {
        viewModel.homeworld.observe(viewLifecycleOwner) { planet ->
            binding.apply {
                planetNameTextView.text = planet.name
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}