package org.ieselcaminas.aitor.pocketrol.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import org.ieselcaminas.aitor.pocketrol.database.Character
import org.ieselcaminas.aitor.pocketrol.database.Repo


class CharactersViewModel : ViewModel() {

    //"Firebase"
    // Instance
    val repo = Repo()

    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>>
        get() = _characters

    init {
        fetchCharactersData()
    }

    //Firebase Data Charge
    fun fetchCharactersData() {
        //Its observing when 'Repo' has recived the data
        repo.getCharacterData().observeForever {
            _characters.value = it
        }
    }

    //Character ClickListener Functions (Adapter)
    private val _navigateToCharacterCard = MutableLiveData<Character>()
    val navigateToCharacterCard: LiveData<Character>
        get() = _navigateToCharacterCard

    fun onCharacterClicked(chr: Character) {
        _navigateToCharacterCard.value = chr
    }
    fun onCharacterCardNavigated() {
        _navigateToCharacterCard.value = null
    }
}
