package com.arif.pokemonlist.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arif.pokemonlist.data.local.database.PokemonDatabase
import com.arif.pokemonlist.data.local.entity.PokemonEntity
import com.arif.pokemonlist.data.remote.ApiService
import com.arif.pokemonlist.data.remote.mediator.PokemonMediator
import com.arif.pokemonlist.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Github https://github.com/devileya/
 * Created by Arif Fadly Siregar
 * Created at 18 February 2024
 */
class PokemonRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val database: PokemonDatabase
) : PokemonRepository {
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getPokemonPagingData(): Flow<PagingData<PokemonEntity>> {
        val dbSource = {
            database.pokemonDao().getByPaging()
        }
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            remoteMediator = PokemonMediator(
                apiService, database
            ),
            pagingSourceFactory = dbSource
        ).flow
    }
}