package com.example.flashcards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.adapters.AddFragmentsAdapter
import com.example.flashcards.databinding.FragmentAddFlashcardsBinding
import com.example.flashcards.viewmodels.AddFlashcardsViewModel


class AddFlashcardsFragment : Fragment() {

    private var _binding: FragmentAddFlashcardsBinding? = null
    private val binding get() = _binding!!

    private val addFlashcardsViewModel: AddFlashcardsViewModel by activityViewModels()

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

        recyclerView = binding.addFlashcardsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = AddFragmentsAdapter { flashcard, whatToDo ->
            if(whatToDo == getString(R.string.delete_flashcard_option)) {
                addFlashcardsViewModel.removeFlashcard(flashcard.id)
            }
            if(whatToDo == getString(R.string.modify_flashcard_option)){
                // 1 - add         2 - edit
                goToNextScreen(flashcard.id, flashcard.term, flashcard.definition, 2)
            }
        }
        recyclerView.adapter = adapter

        addFlashcardsViewModel.flashcards.observe(this.viewLifecycleOwner) {
            it.let {
                adapter.submitList(addFlashcardsViewModel.getFlashcardList())
            }
        }

        binding.addFloatingActionButton.setOnClickListener {
            // 1 - add         2 - edit
            goToNextScreen(0, "", "", 1)


        }

    }

    override fun onDestroy() {
        super.onDestroy()
        addFlashcardsViewModel.createFlashcardList()
        _binding = null
    }

    fun goToNextScreen(id: Int, term: String, definition: String, addOrEdit: Int){
        val action = AddFlashcardsFragmentDirections.actionAddFlashcardsFragmentToCreateFlashcardFragment(id = id,
            initialTerm = term, initialDefinition = definition, addOrEdit = addOrEdit)
        findNavController().navigate(action)
    }

}