package com.example.flashcards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.flashcards.adapters.AddFragmentsAdapter
import com.example.flashcards.data.CreateFlashcardsDatasourceList
import com.example.flashcards.databinding.FragmentAddFlashcardsBinding
import com.example.flashcards.viewmodels.AddFlashcardsViewModel


class AddFlashcardsFragment : Fragment() {

    private var _binding: FragmentAddFlashcardsBinding? = null
    private val binding get() = _binding!!

    private val addFlashcardsViewModel: AddFlashcardsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddFlashcardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val flashcardsList = CreateFlashcardsDatasourceList.flashcardsList

        addFlashcardsViewModel.createFlashcardList()
        binding.addFlashcardsRecyclerView.adapter = AddFragmentsAdapter(requireContext(), addFlashcardsViewModel.flashcards.value!!.toList())
        binding.addFlashcardsRecyclerView.setHasFixedSize(true)

        binding.addFloatingActionButton.setOnClickListener {
            addFlashcardsViewModel.addFlashCard()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        addFlashcardsViewModel.createFlashcardList()
        _binding = null
    }

}