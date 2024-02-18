package com.arif.pokemonlist.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.arif.pokemonlist.data.local.entity.PokemonEntity
import com.arif.pokemonlist.data.mapper.toPokemon
import com.arif.pokemonlist.domain.repository.PokemonRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

/**
 * Github https://github.com/devileya/
 * Created by Arif Fadly Siregar
 * Created at 18 February 2024
 */
@ExperimentalCoroutinesApi
class GetPokemonListUCTest {

    private val dispatcher = StandardTestDispatcher()
    private val testScope = TestScope(dispatcher)

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cancel()
        unmockkAll()
    }

    private fun createMockPagingData(): PagingData<PokemonEntity> {
        return PagingData.from(listOf(
            PokemonEntity(1, "Bulbasaur", "bulbasaur.jpg"),
            PokemonEntity(2, "Charmander", "charmander.jpg"),
            PokemonEntity(3, "Squirtle", "squirtle.jpg")
        ))
    }

    @Test
    fun `invoke should return transformed paging data`() = runTest {
        testScope.launch {
            val repository = mockk<PokemonRepository>(relaxed = true)
            val mockPagingData: PagingData<PokemonEntity> = createMockPagingData()
            coEvery { repository.getPokemonPagingData() } returns flowOf(mockPagingData)
            val getPokemonListUC = GetPokemonListUC(repository)
            val result = getPokemonListUC(NoParam).first()
            coEvery { repository.getPokemonPagingData() }
            val transformedResult = result.map { it }
            val expectedResult = mockPagingData.map { it.toPokemon() }
            assertEquals(expectedResult, transformedResult)
        }
    }
}