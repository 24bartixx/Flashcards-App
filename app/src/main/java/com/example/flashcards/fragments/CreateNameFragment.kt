package com.example.flashcards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.flashcards.databinding.FragmentCreateNameBinding
import com.example.flashcards.viewmodels.AddFlashcardsViewModel

class CreateNameFragment : Fragment() {

    private var _binding: FragmentCreateNameBinding? = null
    private val binding get() = _binding!!

    private val addFlashcardsViewModel: AddFlashcardsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCreateNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.chooseFlashcardsButton.setOnClickListener {
            val name = binding.flashcardsInputName.editText?.text.toString()
            if(!name.isNullOrEmpty()) {
                addFlashcardsViewModel.setCollectionName(name)
                goToNextScreen()
            }
            else {
                binding.flashcardsInputName.error = "Enter a flashcard collection name"
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun goToNextScreen(){
        val action = CreateNameFragmentDirections.actionCreateNameFragmentToAddFlashcardsFragment()
        findNavController().navigate(action)
    }

}