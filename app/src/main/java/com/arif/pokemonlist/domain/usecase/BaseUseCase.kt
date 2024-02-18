package com.arif.pokemonlist.domain.usecase

/**
 * Github https://github.com/devileya/
 * Created by Arif Fadly Siregar
 * Created at 18 February 2024
 */
data object NoParam
interface BaseUseCase<in Parameter, out Result> {
    suspend operator fun invoke(parameter: Parameter): Result
}