package com.example.flashcards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.adapters.AddFragmentsAdapter
import com.example.flashcards.data.CreateFlashcardsDatasourceList
import com.example.flashcards.databinding.FragmentAddFlashcardsBinding
import com.example.flashcards.viewmodels.AddFlashcardsViewModel
import kotlinx.coroutines.launch


class AddFlashcardsFragment : Fragment() {

    private var _binding: FragmentAddFlashcardsBinding? = null
    private val binding get() = _binding!!

    private val addFlashcardsViewModel: AddFlashcardsViewModel by viewModels()

    lateinit var recyclerView: RecyclerView

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

        recyclerView = binding.addFlashcardsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = AddFragmentsAdapter()
        recyclerView.adapter = adapter
        lifecycle.coroutineScope.launch {
            adapter.submitList(addFlashcardsViewModel.flashcards.value)
        }

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