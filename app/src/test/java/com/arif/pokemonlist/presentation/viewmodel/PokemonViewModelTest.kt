package com.arif.pokemonlist.presentation.viewmodel

import androidx.paging.PagingData
import com.arif.pokemonlist.domain.model.Pokemon
import com.arif.pokemonlist.domain.usecase.GetPokemonListUC
import com.arif.pokemonlist.domain.usecase.NoParam
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

/**
 * Github https://github.com/devileya/
 * Created by Arif Fadly Siregar
 * Created at 18 February 2024
 */
@OptIn(ExperimentalCoroutinesApi::class)
class PokemonViewModelTest {

    private val getPokemonListUC = mockk<GetPokemonListUC>(relaxed = true)
    private val mockPagingData: PagingData<Pokemon> = mockk(relaxed = true)

    private val dispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(dispatcher)

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
        unmockkAll()
    }

    @Test
    fun `test getPokemonList invoked`() = runTest {
        testScope.launch {
            coEvery { getPokemonListUC(any()) } returns flowOf(mockPagingData)
            verify { PokemonViewModel(getPokemonListUC) }
            coVerify { getPokemonListUC(NoParam) }
        }
    }

}