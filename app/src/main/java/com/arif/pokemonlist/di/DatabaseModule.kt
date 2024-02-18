package com.arif.pokemonlist.di

import android.app.Application
import androidx.room.Room
import com.arif.pokemonlist.data.local.database.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Github https://github.com/devileya/
 * Created by Arif Fadly Siregar
 * Created at 18 February 2024
 */
@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(
        app: Application
    ): PokemonDatabase {
        return Room.databaseBuilder(app, PokemonDatabase::class.java, "pokemon.db")
            .fallbackToDestructiveMigration()
            .enableMultiInstanceInvalidation()
            .build()
    }
}