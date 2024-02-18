package com.arif.pokemonlist.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Github https://github.com/devileya/
 * Created by Arif Fadly Siregar
 * Created at 18 February 2024
 */
@Entity(tableName = "pokemon_remote_key")
data class RemoteKeyEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val next: String?,
    @ColumnInfo(name = "last_updated")
    val lastUpdated: Long
)
