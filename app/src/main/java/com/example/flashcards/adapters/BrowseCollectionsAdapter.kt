package com.example.flashcards.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcards.R
import com.example.flashcards.model.Collection

class BrowseCollectionsAdapter(private val context: Context, private val flashcardsCollections: List<Collection>,
    private val navigateToNextScreen: (Collection) -> Unit): RecyclerView.Adapter<BrowseCollectionsAdapter.BrowseCollectionsViewHolder>(){

    class BrowseCollectionsViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.collection_name)
        val creatorTextView: TextView = view.findViewById(R.id.creator)
        val wordsCountTextView: TextView = view.findViewById(R.id.how_many_words)
        val collectionCard: androidx.constraintlayout.widget.ConstraintLayout = view.findViewById(R.id.collection_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrowseCollectionsViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.collection_item, parent, false)
        return BrowseCollectionsViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: BrowseCollectionsViewHolder, position: Int) {
        val flashcardsCollection = flashcardsCollections[position]
        holder.nameTextView.text = flashcardsCollection.name
        holder.creatorTextView.text = context.getString(R.string.creator, flashcardsCollection.creator)
        holder.wordsCountTextView.text = context.getString(R.string.words_count, flashcardsCollection.wordsCount)
        holder.collectionCard.setOnClickListener {
            navigateToNextScreen(flashcardsCollection)
        }
    }

    override fun getItemCount(): Int {
        return flashcardsCollections.size
    }


}