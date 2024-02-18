package com.arif.pokemonlist.data.mapper

import com.arif.pokemonlist.BuildConfig.IMAGE_URL
import com.arif.pokemonlist.data.local.entity.PokemonEntity
import com.arif.pokemonlist.data.remote.model.response.PokemonResponse
import com.arif.pokemonlist.domain.model.Pokemon

/**
 * Github https://github.com/devileya/
 * Created by Arif Fadly Siregar
 * Created at 18 February 2024
 */
fun PokemonResponse.toPokemonEntityList(): List<PokemonEntity> {
    return this.results?.map {
        item -> PokemonEntity(name = item.name, image = "$IMAGE_URL/${item.name}.jpg")
    } ?: emptyList()
}

fun PokemonEntity.toPokemon(): Pokemon {
    return Pokemon(
        name = name,
        image = image
    )
}