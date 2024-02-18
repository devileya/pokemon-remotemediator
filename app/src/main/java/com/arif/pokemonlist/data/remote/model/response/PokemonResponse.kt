package com.arif.pokemonlist.data.remote.model.response

/**
 * Github https://github.com/devileya/
 * Created by Arif Fadly Siregar
 * Created at 18 February 2024
 */
data class PokemonResponse (
    val count: Long? = 0,
    val next: String? = "",
    val previous: Any? = "",
    val results: List<Result>? = emptyList()
)

data class Result (
    val name: String = "",
    val url: String = ""
)
