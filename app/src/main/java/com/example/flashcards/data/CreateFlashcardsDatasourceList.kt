package com.example.flashcards.data

import com.example.flashcards.model.Flashcard

class CreateFlashcardsDatasourceList {

    companion object {
        val flashcardsList = mutableListOf<Flashcard>(
            Flashcard(1, "", ""),
            Flashcard(2, "", "")
        )
    }

}