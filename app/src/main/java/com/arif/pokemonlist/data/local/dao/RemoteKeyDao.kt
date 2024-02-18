package com.arif.pokemonlist.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arif.pokemonlist.data.local.entity.RemoteKeyEntity

/**
 * Github https://github.com/devileya/
 * Created by Arif Fadly Siregar
 * Created at 18 February 2024
 */
@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: RemoteKeyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(key: List<RemoteKeyEntity>)

    @Query("select * from pokemon_remote_key where id=:key")
    suspend fun getRemoteKeyId(key: String): RemoteKeyEntity?

    @Query("delete from pokemon_remote_key")
    suspend fun delete()
}