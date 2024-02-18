package com.arif.pokemonlist.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Github https://github.com/devileya/
 * Created by Arif Fadly Siregar
 * Created at 18 February 2024
 */
@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val image: String
)
