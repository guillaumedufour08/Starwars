package com.example.starwars.ui.character.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwars.R
import com.example.starwars.util.StringManager
import com.example.starwars.databinding.FragmentCharacterDetailBinding
import com.example.starwars.viewmodel.CharacterListViewModel
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CharacterDetailFragment : Fragment() {

    private val viewModel : CharacterListViewModel by activityViewModels()
    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private val vehiclesListAdapter = VehiclesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeCharacterRecyclerView()
        viewModel.fetchCharacterPlanetAndVehicules()
        initializeCharacterObserver()
        initializePlanetObserver()
        initializeVehiclesObserver()
    }

    private fun initializeCharacterObserver() {
        viewModel.selectedCharacter.observe(viewLifecycleOwner) { character ->
            binding.apply {
                characterNameTextView.text = character!!.name
                updatedAtTextView.text = root.context.getString(R.string.updated_at, StringManager.formatDate(character.edited))
                genderCardView.setValue(StringManager.findFromName(root, character.gender))
                heightCardView.setValue(root.context.getString(R.string.height, character.height))
            }
        }
    }

    private fun initializeCharacterRecyclerView() {
        binding.vehiclesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = vehiclesListAdapter
        }
    }

    private fun initializeVehiclesObserver() {
        viewModel.vehiclesList.observe(viewLifecycleOwner) { vehicles ->
            lifecycle.coroutineScope.launch {
                binding.apply {
                    vehiclesListAdapter.submitList(vehicles)
                }
            }
        }
    }

    private fun initializePlanetObserver() {
        viewModel.homeworld.observe(viewLifecycleOwner) { planet ->
            lifecycle.coroutineScope.launch {
                binding.apply {
                    if (planet != null) {
                        planetNameTextView.text = planet.name
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.unselectCharacter()
        _binding = null
    }
}