package org.ieselcaminas.aitor.pocketrol.charactercreation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.ieselcaminas.aitor.pocketrol.database.RaceOrClas
import org.ieselcaminas.aitor.pocketrol.database.Repo

class CharacterCreationViewModel: ViewModel() {

    val repo = Repo()

    private val _race = MutableLiveData<MutableList<RaceOrClas>>()
    val race: LiveData<MutableList<RaceOrClas>>
        get() = _race

    private val _clas = MutableLiveData<MutableList<RaceOrClas>>()
    val clas: LiveData<MutableList<RaceOrClas>>
        get() = _clas


    //Firebase Data Charge
    fun fetchRaceOrClasData(what: String) {
        //Its observing when 'Repo' has recived the data
        repo.getRacesOrClases(what).observeForever {
            if (what == "races") {
                _race.value = it
            }
            else if (what == "clases") {
                _clas.value = it
            }
        }
    }
}