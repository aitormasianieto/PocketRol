package org.ieselcaminas.aitor.pocketrol.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore

class Repo {

    fun getCharacterData(): LiveData<MutableList<Character>> {
        val mutableLiveData = MutableLiveData<MutableList<Character>>()

        FirebaseFirestore.getInstance().collection("characters").get().addOnSuccessListener {result ->
            val listData = mutableListOf<Character>()
            for (document in result) {
                val imageUrl = document.getString("imageUrl")
                val name = document.getString("name")
                val race = document.getString("race")

                val character = Character(name!!, race!!, imageUrl!!)
                listData.add(character)
            }
            mutableLiveData.value = listData
        }
        return mutableLiveData
    }
}