package com.arif.pokemonlist.domain.repository

import androidx.paging.PagingData
import com.arif.pokemonlist.data.local.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

/**
 * Github https://github.com/devileya/
 * Created by Arif Fadly Siregar
 * Created at 18 February 2024
 */
interface PokemonRepository {
    suspend fun getPokemonPagingData(): Flow<PagingData<PokemonEntity>>
}