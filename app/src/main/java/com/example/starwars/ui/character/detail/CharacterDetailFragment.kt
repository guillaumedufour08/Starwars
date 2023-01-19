package com.example.starwars.ui.character.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwars.R
import com.example.starwars.util.StringFinder
import com.example.starwars.databinding.FragmentCharacterDetailBinding
import com.example.starwars.util.DateParser
import com.example.starwars.viewmodel.CharacterDetailViewModel

class CharacterDetailFragment : Fragment() {

//    private val viewModel : CharacterListViewModel by activityViewModels()
    private val viewModel : CharacterDetailViewModel by viewModels()
    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private val starshipsListAdapter = StarshipListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        viewModel.setSelectedCharacter(arguments?.getString("characterID")!!)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeCharacterRecyclerView()
        initializeCharacterImageViewContentDescription()
        viewModel.fetchCharacterPlanetAndStarships()
        initializeCharacterObserver()
        initializePlanetObserver()
        initializeStarshipsListObserver()
    }

    private fun initializeCharacterObserver() {
        viewModel.selectedCharacter.observe(viewLifecycleOwner) { character ->
            val date = DateParser.format(character!!.edited)
            binding.apply {
                characterNameTextView.text = character.name
                updatedAtTextView.text = root.context.getString(R.string.updated_at, date)
                genderCardView.setValue(StringFinder.findFromName(root, character.gender))
                heightCardView.setValue(root.context.getString(R.string.height, character.height))
            }
        }
    }

    private fun initializeCharacterRecyclerView() {
        binding.starshipsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = starshipsListAdapter
        }
    }

    private fun initializeStarshipsListObserver() {
        viewModel.starshipList.observe(viewLifecycleOwner) { starships ->
            binding.apply {
                loadingStarshipsProgressBar.visibility =
                    if (viewModel.areStarshipsBeingFetched.value == true) View.VISIBLE else View.GONE
                if (starships.isEmpty()) {
                    emptyStarshipsTextView.visibility = View.VISIBLE
                } else {
                    starshipsRecyclerView.visibility = View.VISIBLE
                    starshipsListAdapter.submitList(starships)
                }
            }
        }
    }

    private fun initializePlanetObserver() {
        viewModel.homeworld.observe(viewLifecycleOwner) { planet ->
            binding.apply {
                loadingHomeworldProgressBar.visibility =
                    if (viewModel.isHomeworldBeingFetched.value == true) View.VISIBLE else View.GONE
                homelandCardView.visibility = View.VISIBLE
                planetNameTextView.text = planet.name
                planetImageView.contentDescription =
                    root.context.getString(R.string.homeworld_image_view_description, planet.name)
            }
        }
    }

    private fun initializeCharacterImageViewContentDescription() {
        val characterName = viewModel.selectedCharacter.value?.name
        binding.apply {
            characterDetailImageView.contentDescription =
                root.context.getString(R.string.character_detail_image_view_description, characterName)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        viewModel.unselectCharacter()
        _binding = null
    }
}

// old « initializeVehiclesListObserver() »
/*
//        testViewModel.vehiclesList.observe(viewLifecycleOwner) { vehicles ->
//                if (testViewModel.areVehiclesBeingFetched.value == false) {
//                    if (vehicles != null) {
//                        binding.apply {
//                            if (vehicles.isEmpty()) {
//                                emptyVehiclesTextView.visibility = View.VISIBLE
//                            } else {
//                                vehiclesRecyclerView.visibility = View.VISIBLE
//                                vehiclesListAdapter.submitList(vehicles)
//                            }
//                        }
//                    }
//                }
//        }
 */