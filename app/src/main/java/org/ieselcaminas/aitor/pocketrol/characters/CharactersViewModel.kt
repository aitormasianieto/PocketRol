package org.ieselcaminas.aitor.pocketrol.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.ieselcaminas.aitor.pocketrol.database.Character
import org.ieselcaminas.aitor.pocketrol.database.Repo


class CharactersViewModel : ViewModel() {

    //"Firebase" Instance
    val repo = Repo()

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var chr = MutableLiveData<Character?>()

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

    //ClickListener Functions
    private val _navigateToCharacterCard = MutableLiveData<Long>()
    val navigateToCharacterCard: LiveData<Long>
        get() = _navigateToCharacterCard

    fun onCharacterClicked(id: Long) {
        _navigateToCharacterCard.value = id
    }

    fun onCharacterCardNavigated() {
        _navigateToCharacterCard.value = null
    }
}
