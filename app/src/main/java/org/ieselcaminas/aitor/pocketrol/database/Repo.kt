package org.ieselcaminas.aitor.pocketrol.database

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore

class Repo {

    fun getCharacterData(): MutableLiveData<List<Character>> {
        val mutableLiveData = MutableLiveData<List<Character>>()

        FirebaseFirestore.getInstance().collection("characters").get().addOnSuccessListener {result ->
            val listData = mutableListOf<Character>()
            for (document in result) {
                val imageUrl = document.getString("imageUrl")
                val name = document.getString("name")
                val race = document.getString("race")
                //val chrId =

                var counter: Long = 1
                val character = Character(counter, imageUrl!!, name!!, race!!,"bruixot")
                listData.add(character)
                counter++
            }
            mutableLiveData.value = listData
        }
        return mutableLiveData
    }
}