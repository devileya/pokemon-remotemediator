package com.arif.pokemonlist.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arif.pokemonlist.data.local.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

/**
 * Github https://github.com/devileya/
 * Created by Arif Fadly Siregar
 * Created at 18 February 2024
 */
@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(movies: List<PokemonEntity>)

    @Query("delete from pokemon")
    suspend fun delete()

    @Query("select * from pokemon order by id asc")
    fun getByPaging() : PagingSource<Int, PokemonEntity>

    @Query("select * from pokemon order by id asc limit :limit")
    fun getByFlow(limit: Int): Flow<List<PokemonEntity>>
}