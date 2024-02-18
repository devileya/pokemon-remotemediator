package com.arif.pokemonlist.data.mapper

import com.arif.pokemonlist.BuildConfig.IMAGE_URL
import com.arif.pokemonlist.data.local.entity.PokemonEntity
import com.arif.pokemonlist.data.remote.model.response.PokemonResponse
import com.arif.pokemonlist.data.remote.model.response.Result
import com.arif.pokemonlist.domain.model.Pokemon
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

/**
 * Github https://github.com/devileya/
 * Created by Arif Fadly Siregar
 * Created at 18 February 2024
 */
class PokemonMapperTest {
    @Nested
    inner class MapperToPokemonEntityList {
        @Test
        fun `with valid response`() {
            // Mocked PokemonResponse
            val response = mockk<PokemonResponse> {
                every { results } returns listOf(
                    Result(name = "Bulbasaur"),
                    Result(name = "Charmander"),
                    Result(name = "Squirtle")
                )
            }

            // Expected list of PokemonEntity
            val expectedList = listOf(
                PokemonEntity(name = "Bulbasaur", image = "$IMAGE_URL/Bulbasaur.jpg"),
                PokemonEntity(name = "Charmander", image = "$IMAGE_URL/Charmander.jpg"),
                PokemonEntity(name = "Squirtle", image = "$IMAGE_URL/Squirtle.jpg")
            )

            // Call the function and assert the result
            assertEquals(expectedList, response.toPokemonEntityList())
        }

        @Test
        fun `with null results`() {
            // Mocked PokemonResponse with null results
            val response = mockk<PokemonResponse> {
                every { results } returns null
            }

            // Call the function and assert the result
            assertEquals(emptyList<PokemonEntity>(), response.toPokemonEntityList())
        }

        @Test
        fun `with empty results`() {
            // Mocked PokemonResponse with empty results
            val response = mockk<PokemonResponse> {
                every { results } returns emptyList()
            }

            // Call the function and assert the result
            assertEquals(emptyList<PokemonEntity>(), response.toPokemonEntityList())
        }
    }

    @Nested
    inner class MapperToPokemon {
        @Test
        fun `with valid entity`() {
            // Mocked PokemonEntity
            val entity = PokemonEntity(name = "Pikachu", image = "$IMAGE_URL/Pikachu.jpg")

            // Expected Pokemon
            val expectedPokemon = Pokemon(name = "Pikachu", image = "$IMAGE_URL/Pikachu.jpg")

            // Call the function and assert the result
            assertEquals(expectedPokemon, entity.toPokemon())
        }
    }
}