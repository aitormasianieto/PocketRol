package org.ieselcaminas.aitor.pocketrol.database

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class Repo {

    private val firebaseFirestoreInstance = FirebaseFirestore.getInstance()
    private val userUid by lazy { FirebaseAuth.getInstance().currentUser?.uid ?: "errorNotUserUID" }

    /*fun getUserUid(): String {
        synchronized(String::class.java) {
            if (!::userUid.isInitialized) {
                FirebaseAuth.getInstance().currentUser?.uid
            }
            return userUid
        }
    }*/

    /*fun getCharacterData(): MutableLiveData<List<Character>> {
        val mutableLiveData = MutableLiveData<List<Character>>()

        firebaseFirestoreInstance.collection("users").document(userUid).collection("characters").get().addOnSuccessListener { result ->

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
    }*/

    fun getCharacterData(): MutableLiveData<List<Character>> {
        val mutableLiveData = MutableLiveData<List<Character>>()

        firebaseFirestoreInstance.collection("users").document(userUid).collection("characters").addSnapshotListener { snapshots, ffe ->
            for (dc in snapshots!!.documentChanges) {
                when (dc.type) {
                    DocumentChange.Type.ADDED -> {
                        //mis mierdas
                        //dc.document.*
                        //dc.document.getString()
                    }
                }

        }


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

        firebaseFirestoreInstance.collection("users").document(userUid).get().addOnSuccessListener { b = true }

        return b
    }
    fun createUserFirebase() {
        val data: Map<String, String> = mapOf(Pair("name", FirebaseAuth.getInstance().currentUser?.displayName ?: "errorNotUserName"))

        firebaseFirestoreInstance.collection("users")
            .document(userUid).set(data)
    }
}