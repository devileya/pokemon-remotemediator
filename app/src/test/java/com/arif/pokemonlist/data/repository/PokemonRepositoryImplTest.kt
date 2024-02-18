package com.arif.pokemonlist.data.repository

import androidx.paging.PagingSource
import com.arif.pokemonlist.data.local.database.PokemonDatabase
import com.arif.pokemonlist.data.local.entity.PokemonEntity
import com.arif.pokemonlist.data.remote.ApiService
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

/**
 * Github https://github.com/devileya/
 * Created by Arif Fadly Siregar
 * Created at 18 February 2024
 */
@OptIn(ExperimentalCoroutinesApi::class)
class PokemonRepositoryImplTest {

    private val apiService = mockk<ApiService>(relaxed = true)
    private val database = mockk<PokemonDatabase>(relaxed = true)
    private val pagingSource = mockk<PagingSource<Int, PokemonEntity>>(relaxed = true)

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

    private fun dummyData(): List<PokemonEntity> {
        return listOf(
            PokemonEntity(1, "Bulbasaur", "bulbasaur.jpg"),
            PokemonEntity(2, "Charmander", "charmander.jpg"),
            PokemonEntity(3, "Squirtle", "squirtle.jpg")
        )
    }

    @Test
    fun getPokemonPagingData() = runTest {
        testScope.launch {
            val repository = PokemonRepositoryImpl(apiService, database)
            val dummyData = dummyData()
            coEvery { pagingSource.load(any()) } returns PagingSource.LoadResult.Page(
                dummyData,
                null,
                null
            )
            coEvery { database.pokemonDao().getByPaging() } returns pagingSource
            every { database.pokemonDao().getByPaging() } returns pagingSource
            val result = repository.getPokemonPagingData().first()
            assertEquals(dummyData, result)
        }
    }
}