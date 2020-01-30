package org.ieselcaminas.aitor.pocketrol.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore


class CharactersViewModel : ViewModel() {

    var db = FirebaseFirestore.getInstance() //ns donde ponerlo bb


    private val _navigateToCharacterCard = MutableLiveData<Long>()
    val navigateToSleepDataQuality: LiveData<Long>
        get() = _navigateToCharacterCard

    fun onCharacterClicked(id: Long) {
        _navigateToCharacterCard.value = id
    }

    fun onCharacterNavigated() {
        _navigateToCharacterCard.value = null
    }
}
