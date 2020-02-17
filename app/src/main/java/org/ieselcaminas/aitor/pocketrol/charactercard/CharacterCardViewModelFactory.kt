package org.ieselcaminas.aitor.pocketrol.charactercard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.ieselcaminas.aitor.pocketrol.database.Character

/**
 * Simple ViewModel factory that provides the MarsProperty and context to the ViewModel.
 */
class CharacterCardViewModelFactory(private val character: Character) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterCardViewModel::class.java)) {
            return CharacterCardViewModel(character) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
