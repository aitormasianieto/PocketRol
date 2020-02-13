package org.ieselcaminas.aitor.pocketrol.database

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Repo { /**Es mejor que cada uno acceda a su Repo o mejor hago un singleton para que haya un unico Repo????*/

    private val INSTANCE = FirebaseFirestore.getInstance()
    private val userUid by lazy { FirebaseAuth.getInstance().currentUser?.uid ?: "errorNotUserUID" }

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

        INSTANCE.collection("users").document(userUid).collection("characters").get().addOnSuccessListener { result ->

            val listData = mutableListOf<Character>()
            for (document in result) {
                val imageUrl = document.getString("imageUrl")
                val name = document.getString("name")
                val race = document.getString("race")
                val chrId = document.id

                val character = Character(chrId, imageUrl!!, name!!, race!!)
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

        INSTANCE.collection("users").document(userUid).get().addOnSuccessListener { b = true }

        return b
    }
    fun createUserFirebase() {
        val data: Map<String, String> = mapOf(Pair("name", FirebaseAuth.getInstance().currentUser?.displayName ?: "errorNotUserName"))

        INSTANCE.collection("users")
            .document(userUid).set(data)
    }
}