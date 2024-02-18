package com.arif.pokemonlist

import android.app.Application
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


/**
 * Github https://github.com/devileya/
 * Created by Arif Fadly Siregar
 * Created at 18 February 2024
 */
@HiltAndroidApp
class PokemonApp: Application() {
    @Inject
    @ApplicationContext lateinit var context: Context

    override fun onCreate() {
        super.onCreate()
        Glide.get(this).setMemoryCategory(MemoryCategory.HIGH)
    }
}