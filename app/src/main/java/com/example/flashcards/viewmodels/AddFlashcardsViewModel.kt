package com.example.flashcards.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.flashcards.model.Flashcard

class AddFlashcardsViewModel: ViewModel() {

    private val _collectionName = MutableLiveData<String>()
    val collectionName: LiveData<String> = _collectionName

    private val _flashcards = MutableLiveData<MutableList<Flashcard>>()
    val flashcards: LiveData<MutableList<Flashcard>> = _flashcards

    private val _availableID = MutableLiveData<Int>(1)
    private val availableID: LiveData<Int> = _availableID

    init {
        _flashcards.value = mutableListOf<Flashcard>()
    }

    fun setCollectionName(name: String) {
        _collectionName.value = name
    }

    fun createFlashcardList(){
        val list = mutableListOf(Flashcard(6, "Siuu", "Yea"))
        _flashcards.value = list
    }

    fun addFlashCard(term: String, definition: String) {
        val list = _flashcards.value!!
        var newAvailableID = _availableID.value
        val id = newAvailableID!!
        newAvailableID = newAvailableID + 1
        _availableID.value = newAvailableID!!
        //val id = list.size + 1
        list?.add(Flashcard(id, term, definition))
        _flashcards.value = list
    }

    fun modifyFlashcard(id: Int, term: String, definition: String) {
        val list = _flashcards.value!!
        val iterator = list.listIterator()
        while(iterator.hasNext()){
            val flashcard = iterator.next()
            if(flashcard.id == id){
                iterator.set(Flashcard(id, term, definition))
            }
        }
        _flashcards.value = list
    }

    fun removeFlashcard(id: Int){
        val list = _flashcards.value!!
        val iterator = list.iterator()
        while(iterator.hasNext()) {
            val flashcard = iterator.next()
            if(flashcard.id == id){
                //list.removeAt(id)
                iterator.remove()
            }
        }
        _flashcards.value = list
    }

    fun getFlashcardList(): List<Flashcard>{
        return flashcards.value!!.toList()
    }
}