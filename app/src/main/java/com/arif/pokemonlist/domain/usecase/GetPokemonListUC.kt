package com.arif.pokemonlist.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.arif.pokemonlist.data.mapper.toPokemon
import com.arif.pokemonlist.domain.model.Pokemon
import com.arif.pokemonlist.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Github https://github.com/devileya/
 * Created by Arif Fadly Siregar
 * Created at 18 February 2024
 */
typealias GetPokemonListUseCase = BaseUseCase<NoParam, Flow<PagingData<Pokemon>>>
class GetPokemonListUC @Inject constructor(private val repository: PokemonRepository): GetPokemonListUseCase {
    override suspend fun invoke(parameter: NoParam): Flow<PagingData<Pokemon>> {
        return repository.getPokemonPagingData().map { pagingData ->
            pagingData.map { it.toPokemon() }
        }
    }
}