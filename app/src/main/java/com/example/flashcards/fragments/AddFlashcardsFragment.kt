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
import com.example.flashcards.adapters.AddFragmentsAdapter
import com.example.flashcards.data.CreateFlashcardsDatasourceList
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

        val flashcardsList = CreateFlashcardsDatasourceList.flashcardsList

        /**
        Log.d("Dupa", addFlashcardsViewModel.getFlashcardList().size.toString())
        Log.d("Dupa", addFlashcardsViewModel.getFlashcardList()[0].term.toString())
        Log.d("Dupa", addFlashcardsViewModel.getFlashcardList()[0].definition.toString())
        */

        recyclerView = binding.addFlashcardsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = AddFragmentsAdapter {
            addFlashcardsViewModel.removeFlashcard(it.id)
        }
        recyclerView.adapter = adapter

        addFlashcardsViewModel.flashcards.observe(this.viewLifecycleOwner) {
            it.let {
                adapter.submitList(addFlashcardsViewModel.getFlashcardList())
            }
        }

        /**
        lifecycle.coroutineScope.launch {
            adapter.submitList(addFlashcardsViewModel.flashcards.value)
        }
        */

        binding.addFloatingActionButton.setOnClickListener {
            goToNextScreen()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        addFlashcardsViewModel.createFlashcardList()
        _binding = null
    }

    fun goToNextScreen(){
        val action = AddFlashcardsFragmentDirections.actionAddFlashcardsFragmentToCreateFlashcardFragment()
        findNavController().navigate(action)
    }

}