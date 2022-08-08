package com.example.flashcards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flashcards.databinding.FragmentWhichFlashcardsBinding

class WhichFlashcardsFragment : Fragment() {

    private var _binding: FragmentWhichFlashcardsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWhichFlashcardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.allFlashcardsButton.setOnClickListener {
            // val action = Layout.Directions
            // findNavController().navigate(action)
        }

        binding.learnedFlashcardsButton.setOnClickListener {

        }

        binding.unlearnedFlashcardsButton.setOnClickListener {

        }

    }

}