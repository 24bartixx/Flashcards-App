package com.example.flashcards.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.model.Flashcard
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AddFragmentsAdapter(private val context: Context, private val flashcards: List<Flashcard>)
    : RecyclerView.Adapter<AddFragmentsAdapter.AddFragmentsViewHolder>() {

    // ViewHolder
    class AddFragmentsViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val termTextLayout: TextInputLayout = view.findViewById(R.id.flashcard_input_term)
        val definitionTextLayout: TextInputLayout = view.findViewById(R.id.flashcard_input_definition)
        val termEditText: TextInputEditText = view.findViewById(R.id.flashcard_input_term_edit_text)
        val definitionEditText: TextInputEditText = view.findViewById(R.id.flashcard_input_definition_edit_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddFragmentsViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.add_flashcards_item, parent, false)
        return AddFragmentsViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: AddFragmentsViewHolder, position: Int) {
        holder.termEditText.setText(flashcards[position].term)
        holder.definitionEditText.setText(flashcards[position].definition)
    }

    override fun getItemCount(): Int {
        return flashcards.size
    }

}