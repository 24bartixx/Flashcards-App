package com.example.flashcards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.flashcards.databinding.FragmentMainScreenBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainScreenFragment : Fragment() {

    // ViewBinding
    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    // Cloud Firestore instance
    val firebase = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.studyButton.setOnClickListener {
            val action = MainScreenFragmentDirections.actionMainScreenFragmentToBrowseColletionsFragment()
            findNavController().navigate(action)
        }

        binding.createButton.setOnClickListener {
            val action = MainScreenFragmentDirections.actionMainScreenFragmentToCreateNameFragment()
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}