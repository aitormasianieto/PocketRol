package org.ieselcaminas.aitor.pocketrol.characters

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore


class CharactersViewModel : ViewModel() {

    var db = FirebaseFirestore.getInstance()
}
