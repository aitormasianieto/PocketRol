package org.ieselcaminas.aitor.pocketrol.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.ieselcaminas.aitor.pocketrol.database.Character
import org.ieselcaminas.aitor.pocketrol.database.Repo


class CharactersViewModel : ViewModel() {

    //Firebase Data Charge
    val repo = Repo()
    fun fetchCharacterData(): LiveData<MutableList<Character>> {
        val mutableLiveData = MutableLiveData<MutableList<Character>>()

        //Its observing when 'Repo' has recived the data
        repo.getCharacterData().observeForever {
            mutableLiveData.value = it
        }
        return mutableLiveData
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
