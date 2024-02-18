package com.arif.pokemonlist.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arif.pokemonlist.R
import com.arif.pokemonlist.databinding.ItemPokemonBinding
import com.arif.pokemonlist.domain.model.Pokemon
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

/**
 * Github https://github.com/devileya/
 * Created by Arif Fadly Siregar
 * Created at 18 February 2024
 */
class PokemonListAdapter :
    PagingDataAdapter<Pokemon, PokemonListAdapter.SelfViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelfViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelfViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelfViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.onBind(data)
        }
    }

    inner class SelfViewHolder(private val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Pokemon) = with(binding) {
            Glide.with(itemView.context)
                .load(item.image)
                .error(R.drawable.ic_launcher_background)
                .apply(RequestOptions.fitCenterTransform())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivPokemon)

            tvName.text = item.name
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}