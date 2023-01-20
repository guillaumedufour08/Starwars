package com.example.starwars.ui.character.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwars.R
import com.example.starwars.util.StringFinder
import com.example.starwars.databinding.FragmentCharacterDetailBinding
import com.example.starwars.util.DateParser
import com.example.starwars.viewmodel.CharacterDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {
    private val viewModel: CharacterDetailViewModel by viewModels()
    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private val starshipsListAdapter = StarshipListAdapter()
    private val args: CharacterDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        viewModel.getCharacterWithEntities(args.characterUID)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeCharacterRecyclerView()
        initializeCharacterObserver()
        initializePlanetObserver()
        initializeStarshipsListObserver()
        initializeFetchingStarshipsObserver()
        initializeFetchingHomeworldObserver()
    }

    private fun initializeCharacterObserver() {
        viewModel.selectedCharacter.observe(viewLifecycleOwner) { character ->
            val date = DateParser.format(character!!.edited)
            binding.apply {
                characterNameTextView.text = character.name
                updatedAtTextView.text = root.context.getString(R.string.updated_at, date)
                genderCardView.setValue(StringFinder.findFromName(root, character.gender))
                heightCardView.setValue(root.context.getString(R.string.height, character.height))
                characterDetailImageView.contentDescription =
                    root.context.getString(R.string.character_detail_image_view_description, character.name)
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
                if (starships.isEmpty()) {
                    emptyStarshipsTextView.visibility = View.VISIBLE
                } else {
                    starshipsRecyclerView.visibility = View.VISIBLE
                    starshipsListAdapter.submitList(starships)
                }
            }
        }
    }

    private fun initializeFetchingHomeworldObserver() {
        viewModel.isHomeworldRepositoryInUse.observe(viewLifecycleOwner) { isHomeworldBeingFetched ->
            binding.apply {
                loadingHomeworldProgressBar.visibility =
                    if (isHomeworldBeingFetched == true) View.VISIBLE else View.GONE
            }
        }
    }

    private fun initializeFetchingStarshipsObserver() {
        viewModel.isStarshipRepositoryInUse.observe(viewLifecycleOwner) { areStarshipsBeingFetched ->
            binding.apply {
                loadingStarshipsProgressBar.visibility =
                    if (areStarshipsBeingFetched == true) View.VISIBLE else View.GONE
            }
        }
    }

    private fun initializePlanetObserver() {
        viewModel.homeworld.observe(viewLifecycleOwner) { planet ->
            binding.apply {
                homelandCardView.visibility = View.VISIBLE
                planetNameTextView.text = planet.name
                planetImageView.contentDescription =
                    root.context.getString(R.string.homeworld_image_view_description, planet.name)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
