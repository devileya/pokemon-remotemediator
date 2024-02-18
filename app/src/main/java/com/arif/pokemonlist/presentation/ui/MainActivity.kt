package com.arif.pokemonlist.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.arif.pokemonlist.databinding.ActivityMainBinding
import com.arif.pokemonlist.presentation.ui.adapter.LoadingStateAdapter
import com.arif.pokemonlist.presentation.ui.adapter.PokemonListAdapter
import com.arif.pokemonlist.presentation.viewmodel.PokemonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val pokemonViewModel by viewModels<PokemonViewModel>()
    private val adapter by lazy { PokemonListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        observeData()
    }

    private fun observeData() = lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            pokemonViewModel.pokemonList.collectLatest { adapter.submitData(lifecycle, it) }
        }
    }

    private fun setupAdapter() = with(binding) {
        rvPokemon.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter { adapter.retry() }
        )
    }
}