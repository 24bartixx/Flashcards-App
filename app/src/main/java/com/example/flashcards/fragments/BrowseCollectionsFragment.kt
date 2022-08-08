package com.example.flashcards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.adapters.BrowseCollectionsAdapter
import com.example.flashcards.databinding.FragmentBrowseCollectionsBinding
import com.example.flashcards.model.Collection

class BrowseCollectionsFragment : Fragment() {

    private var _binding: FragmentBrowseCollectionsBinding? = null
    private val binding get() = _binding!!

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBrowseCollectionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Navigate to create a collection */
        binding.addCollectionButton.setOnClickListener {
            val action = BrowseCollectionsFragmentDirections.actionBrowseColletionsFragmentToCreateNameFragment()
            findNavController().navigate(action)
        }

        /** Populate the recyclerview */
        val flashcardCollection = listOf(
            Collection(id = 1, creator = "Maverick", name = "Top gun", wordsCount = 6),
            Collection(id = 2, creator = "Thomas Shelby", name = "Peaky Blinders", wordsCount = 201),
            Collection(id = 3, creator = "Heisenberg", name = "Breaking bad", wordsCount = 56),
            Collection(id = 4, creator = "Andrew Tate", name = "Top G", wordsCount = 2135),
            Collection(id = 5, creator = "Night Lovell", name = "Dark music", wordsCount = 37),
        )
        val recyclerView = binding.collectionsRecyclerView
        recyclerView.adapter = BrowseCollectionsAdapter(requireContext(), flashcardCollection, {collection ->
            val action = BrowseCollectionsFragmentDirections.actionBrowseColletionsFragmentToDecideCollectionFragment(
                id = collection.id, name = collection.name)
            findNavController().navigate(action)
        })
        recyclerView.setHasFixedSize(true)

    }

}