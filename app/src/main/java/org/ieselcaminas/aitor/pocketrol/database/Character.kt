package org.ieselcaminas.aitor.pocketrol.database

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(
    val chrId: String,
    val imageUrl: String,
    val name: String,
    val race: String = "Human",
    val clas: String = "Paladin",
    val level: Int = 0,
    val inventory: List<String> = listOf(),
    val spellBook: List<String> = listOf()
) : Parcelable