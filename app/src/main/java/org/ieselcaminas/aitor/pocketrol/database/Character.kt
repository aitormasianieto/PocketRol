package org.ieselcaminas.aitor.pocketrol.database

data class Character(val imageUrl: String, val name: String, val race: String,
                     val clas: String,
                     val level: Int = 0,
                     val inventory: List<String>,
                     val spellBook: List<String>) {}