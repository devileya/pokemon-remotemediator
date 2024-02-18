package com.arif.pokemonlist.data.remote

import com.arif.pokemonlist.data.remote.model.response.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Github https://github.com/devileya/
 * Created by Arif Fadly Siregar
 * Created at 18 February 2024
 */
interface ApiService {
    @GET
    suspend fun getPokemon(
        @Url url: String,
    ): PokemonResponse
}