package com.arif.pokemonlist.di

import com.arif.pokemonlist.data.repository.PokemonRepositoryImpl
import com.arif.pokemonlist.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Github https://github.com/devileya/
 * Created by Arif Fadly Siregar
 * Created at 18 February 2024
 */
@Suppress("unused")
@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPokemonRepository(pokemonRepositoryImpl: PokemonRepositoryImpl): PokemonRepository
}