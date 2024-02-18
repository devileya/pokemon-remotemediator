package com.arif.pokemonlist.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator
import com.arif.pokemonlist.data.local.dao.PokemonDao
import com.arif.pokemonlist.data.local.dao.RemoteKeyDao
import com.arif.pokemonlist.data.local.database.PokemonDatabase
import com.arif.pokemonlist.data.local.entity.RemoteKeyEntity
import com.arif.pokemonlist.data.remote.ApiService
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

/**
 * Github https://github.com/devileya/
 * Created by Arif Fadly Siregar
 * Created at 18 February 2024
 */
@ExperimentalPagingApi
@OptIn(ExperimentalCoroutinesApi::class)
class PokemonMediatorTest {

    private val database = mockk<PokemonDatabase>(relaxed = true)
    private val pokemonDao = mockk<PokemonDao>(relaxed = true)
    private val remoteKeyDao = mockk<RemoteKeyDao>(relaxed = true)
    private val apiService = mockk<ApiService>(relaxed = true)

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

    @Nested
    inner class Initialize {
        @Test
        fun `when remote key is null`() = runTest {
            testScope.launch {
                every { database.remoteKeyDao() } returns remoteKeyDao
                coEvery { remoteKeyDao.getRemoteKeyId(any()) } returns null

                val mediator = PokemonMediator(mockk(), database)
                val result = mediator.initialize()

                assertEquals(RemoteMediator.InitializeAction.LAUNCH_INITIAL_REFRESH, result)
            }
        }

        @Test
        fun `when cache timeout has expired`() = runTest {
            testScope.launch {
                every { database.remoteKeyDao() } returns remoteKeyDao
                coEvery { remoteKeyDao.getRemoteKeyId(any()) } returns RemoteKeyEntity(
                    id = "pokemon",
                    next = "",
                    lastUpdated = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(2)
                )

                val mediator = PokemonMediator(mockk(), database)
                val result = mediator.initialize()

                assertEquals(RemoteMediator.InitializeAction.LAUNCH_INITIAL_REFRESH, result)
            }
        }

        @Test
        fun `when cache timeout has not expired`() = runTest {
            testScope.launch {
                every { database.remoteKeyDao() } returns remoteKeyDao
                coEvery { remoteKeyDao.getRemoteKeyId(any()) } returns RemoteKeyEntity(
                    id = "pokemon",
                    next = "",
                    lastUpdated = System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(30)
                )

                val mediator = PokemonMediator(mockk(), database)
                val result = mediator.initialize()

                assertEquals(RemoteMediator.InitializeAction.SKIP_INITIAL_REFRESH, result)
            }
        }
    }

}