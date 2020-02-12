package org.ieselcaminas.aitor.pocketrol.database

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class Repo {

    private val INSTANCE = FirebaseFirestore.getInstance()
    private val userUid by lazy { FirebaseAuth.getInstance().currentUser?.uid ?: "admin" }

    /*fun getUserUid(): String {
        synchronized(String::class.java) {
            if (!::userUid.isInitialized) {
                FirebaseAuth.getInstance().currentUser?.uid
            }
            return userUid
        }
    }*/

    fun getCharacterData(): MutableLiveData<List<Character>> {
        val mutableLiveData = MutableLiveData<List<Character>>()

        checkUserExistence()

        INSTANCE.collection("users").document(userUid).collection("characters").get().addOnSuccessListener { result ->

            val listData = mutableListOf<Character>()
            for (document in result) {
                val imageUrl = document.getString("imageUrl")
                val name = document.getString("name")
                val race = document.getString("race")
                val chrId = document.id

                val character = Character(chrId, imageUrl!!, name!!, race!!,"bruixot")
                listData.add(character)
            }
            mutableLiveData.value = listData
        }
        return mutableLiveData
    }

    fun checkUserExistence() {
        if (!isUserInFirebase()) {
            createUserFirebase()
        }
    }

    fun isUserInFirebase(): Boolean {
        var b = false

        INSTANCE.collection("users").get().addOnSuccessListener { result ->

            for (document in result) {
                if (document.id == userUid) {
                    b = true
                }
            }
        }
        return b
    }

    fun createUserFirebase() {
        var data: Map<String, String> = mapOf(Pair("name", FirebaseAuth.getInstance().currentUser?.displayName ?: "user"))

        INSTANCE.collection("users")
            .document(userUid).set(data)
    }
}