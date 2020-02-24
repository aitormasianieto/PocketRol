package org.ieselcaminas.aitor.pocketrol.database

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

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

    fun getCharacterData(): MutableLiveData<List<Character>> {
        val mutableLiveData = MutableLiveData<List<Character>>()
        val listData = mutableListOf<Character>()

        firebaseFirestoreInstance.collection("users").document(userUid).collection("characters").addSnapshotListener { snapshots, ffe ->
            for (dC in snapshots!!.documentChanges) {
                val doc = dC.document

                when (dC.type) {
                    DocumentChange.Type.ADDED -> {
                        val chrId = doc.id
                        val imageUrl = doc.getString("imageUrl")
                        val name = doc.getString("name")
                        val race = doc.getString("race")
                        val clas = doc.getString("clas")

                        listData.add(
                            Character(
                                chrId,
                                imageUrl!!,
                                name!!,
                                race!!,
                                clas!!
                            )
                        )
                    }
                    DocumentChange.Type.MODIFIED -> {
                        val chrId = doc.id
                        val imageUrl = doc.getString("imageUrl")
                        val name = doc.getString("name")
                        val race = doc.getString("race")
                        val clas = doc.getString("clas")


                        val index = listData.indexOf(listData.find { it.chrId == doc.id })
                        listData[index] = Character(
                            chrId,
                            imageUrl!!,
                            name!!,
                            race!!,
                            clas!!
                        )
                    }
                    DocumentChange.Type.REMOVED -> {
                        listData.removeAt(listData.indexOf(listData.find { it.chrId == doc.id  }))
                    }
                }

            }
            mutableLiveData.value = listData
        }
        return mutableLiveData
    }

    fun setCharacter(character: Character) {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap.put("imageUrl", character.imageUrl)
        hashMap.put("name", character.name)
        hashMap.put("race", character.race)
        hashMap.put("clas", character.clas)

        if (character.chrId == "") {
            firebaseFirestoreInstance.collection("users").document(userUid).collection("characters").add(hashMap)
        }
        else {
            firebaseFirestoreInstance.collection("users").document(userUid).collection("characters").document(character.chrId).set(hashMap)
        }
    }

    fun getRacesOrClases(what: String): MutableLiveData<MutableList<RaceOrClas>> {
        val mutableLiveData: MutableLiveData<MutableList<RaceOrClas>> = MutableLiveData()
        val list: MutableList<RaceOrClas> = ArrayList()

        firebaseFirestoreInstance.collection(what).orderBy("name", Query.Direction.ASCENDING).get().addOnSuccessListener { things ->
            for (t in things) {
                list.add(
                    RaceOrClas(
                        t.getString("imageUrl")!!,
                        t.getString("name")!!
                    )
                )
            }
            mutableLiveData.value = list
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