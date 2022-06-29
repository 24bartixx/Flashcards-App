package com.example.flashcards.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flashcards.model.Flashcard

class AddFlashcardsViewModel: ViewModel() {

    private val _collectionName = MutableLiveData<String>("")
    val collectionName: LiveData<String> = _collectionName

    private val _flashcards = MutableLiveData<MutableList<Flashcard>>()
    val flashcards: LiveData<MutableList<Flashcard>> = _flashcards

    fun setCollectionName(name: String) {
        _collectionName.value = name
    }

    fun createFlashcardList(){
        val list = mutableListOf(Flashcard("", ""))
        _flashcards.value = list
    }

    fun addFlashCard() {
        val list = _flashcards.value
        list?.add(Flashcard("", ""))
        _flashcards.value = list!!
    }

}