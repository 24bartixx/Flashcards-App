package com.example.flashcards.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.databinding.AddFlashcardsItemBinding
import com.example.flashcards.model.Flashcard

class AddFragmentsAdapter: ListAdapter<Flashcard, AddFragmentsAdapter.AddFragmentsViewHolder>(DiffCallback) {

    class AddFragmentsViewHolder(private var binding: AddFlashcardsItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(flashcard: Flashcard) {
            binding.flashcardInputTermEditText.setText(flashcard.term)
            binding.flashcardInputDefinitionEditText.setText(flashcard.definition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddFragmentsViewHolder {

        return AddFragmentsViewHolder(AddFlashcardsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: AddFragmentsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Flashcard>() {

            override fun areItemsTheSame(oldItem: Flashcard, newItem: Flashcard): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Flashcard, newItem: Flashcard): Boolean {
                return newItem.term == oldItem.term
            }

        }
    }


}