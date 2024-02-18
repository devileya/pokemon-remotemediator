package com.arif.pokemonlist.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.arif.pokemonlist.BuildConfig.BASE_URL
import com.arif.pokemonlist.data.local.database.PokemonDatabase
import com.arif.pokemonlist.data.local.entity.PokemonEntity
import com.arif.pokemonlist.data.local.entity.RemoteKeyEntity
import com.arif.pokemonlist.data.mapper.toPokemonEntityList
import com.arif.pokemonlist.data.remote.ApiService
import java.util.concurrent.TimeUnit

/**
 * Github https://github.com/devileya/
 * Created by Arif Fadly Siregar
 * Created at 18 February 2024
 */
@OptIn(ExperimentalPagingApi::class)
class PokemonMediator(
    private val apiService: ApiService,
    private val database: PokemonDatabase
) : RemoteMediator<Int, PokemonEntity>() {

    private val remoteKeyDao = database.remoteKeyDao()
    private val pokemonDao = database.pokemonDao()

    override suspend fun initialize(): InitializeAction {
        val remoteKey: RemoteKeyEntity = database.withTransaction {
            remoteKeyDao.getRemoteKeyId("pokemon")
        } ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        val cacheTimeout = TimeUnit.HOURS.convert(1, TimeUnit.MILLISECONDS)

        return if((System.currentTimeMillis() - remoteKey.lastUpdated) >= cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, PokemonEntity>): MediatorResult {
        return try {
            val nextUrl = when(loadType) {
                LoadType.REFRESH -> {
                    "$BASE_URL/v2/pokemon?offset=0&limit=${state.config.pageSize}"
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.getRemoteKeyId("pokemon")
                    } ?: return MediatorResult.Success(true)

                    if(remoteKey.next == null) {
                        return MediatorResult.Success(true)
                    }

                    remoteKey.next
                }
            }

            val result = apiService.getPokemon(nextUrl)

            database.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    pokemonDao.delete()
                }

                val nextPage = if(result.results?.isEmpty() == true) {
                    null
                } else {
                    result.next
                }

                val pokemonEntities = result.toPokemonEntityList()

                remoteKeyDao.insert(RemoteKeyEntity(
                    id = "pokemon",
                    next = nextPage,
                    lastUpdated = System.currentTimeMillis()
                ))
                pokemonDao.insertList(pokemonEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = result.results?.isEmpty() == true
            )
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }
}