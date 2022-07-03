package com.example.flashcards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.flashcards.R
import com.example.flashcards.databinding.FragmentCreateFlashcardBinding
import com.example.flashcards.viewmodels.AddFlashcardsViewModel

class CreateFlashcardFragment : Fragment() {

    private var _binding: FragmentCreateFlashcardBinding? = null
    private val binding get() = _binding!!

    private val addFlashcardsViewModel: AddFlashcardsViewModel by activityViewModels()

    val args: CreateFlashcardFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCreateFlashcardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.termTextInput.editText?.setText(args.initialTerm)
        binding.definitionTextInput.editText?.setText(args.initialDefinition)

        if(args.addOrEdit == 2) binding.addFlashcardButton.text = getString(R.string.modify_flashcard_button_text)

        binding.addFlashcardButton.setOnClickListener {

            var term = binding.termTextInput.editText?.text.toString()
            var definition = binding.definitionTextInput.editText?.text.toString()

            if(term.isNullOrEmpty()  || definition.isNullOrEmpty()){

                if(term.isNullOrEmpty()){
                   binding.termTextInput.error = "Enter the term"
                }
                else {
                    binding.termTextInput.isErrorEnabled = false
                }

                if(definition.isNullOrEmpty()){
                    binding.definitionTextInput.error = "Enter the definition"
                } else {
                    binding.definitionTextInput.isErrorEnabled = false
                }

            }   else    {
                if (args.addOrEdit == 1) {
                    addFlashcardsViewModel.addFlashCard(term, definition)
                } else {
                    addFlashcardsViewModel.modifyFlashcard(args.id, term, definition)
                }
                goToNextScreen()
            }

            term = ""
            definition = ""

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun goToNextScreen(){
        val action = CreateFlashcardFragmentDirections.actionCreateFlashcardFragmentToAddFlashcardsFragment()
        findNavController().navigate(action)
    }

}