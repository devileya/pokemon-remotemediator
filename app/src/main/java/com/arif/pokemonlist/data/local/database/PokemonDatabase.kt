package com.arif.pokemonlist.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arif.pokemonlist.data.local.dao.PokemonDao
import com.arif.pokemonlist.data.local.dao.RemoteKeyDao
import com.arif.pokemonlist.data.local.entity.PokemonEntity
import com.arif.pokemonlist.data.local.entity.RemoteKeyEntity

/**
 * Github https://github.com/devileya/
 * Created by Arif Fadly Siregar
 * Created at 18 February 2024
 */
@Database(
    entities = [
        PokemonEntity::class,
        RemoteKeyEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao() : PokemonDao
    abstract fun remoteKeyDao() : RemoteKeyDao
}