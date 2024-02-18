package com.arif.pokemonlist.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.arif.pokemonlist.domain.model.Pokemon
import com.arif.pokemonlist.domain.usecase.GetPokemonListUC
import com.arif.pokemonlist.domain.usecase.NoParam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Github https://github.com/devileya/
 * Created by Arif Fadly Siregar
 * Created at 18 February 2024
 */
@HiltViewModel
class PokemonViewModel @Inject constructor(private val getPokemonListUC: GetPokemonListUC): ViewModel() {
    private val _pokemonList = MutableStateFlow<PagingData<Pokemon>>(PagingData.empty())
    val pokemonList get() = _pokemonList

    init {
        getPokemonList()
    }

    private fun getPokemonList() = viewModelScope.launch {
        getPokemonListUC(NoParam).collectLatest { pagingData ->
            _pokemonList.value = pagingData
        }
    }
}