package com.hefesto.pokedex.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hefesto.pokedex.R
import com.hefesto.pokedex.data.Pokemon
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_pokemon.view.*

class PokemonAdapter(
    private val pokemons: List<Pokemon>,
    private val onItemClick: (Pokemon) -> Unit
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItemView(pokemon: Pokemon, onItemClick: (Pokemon) -> Unit) {
            with(itemView) {
                tvName.text = pokemon.name
                tvNumber.text =
                    context.getString(R.string.pokemon_number_format).format(pokemon.number)

                Picasso.get().load(pokemon.imageUrl).into(ivImage)

                setOnClickListener { onItemClick(pokemon) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_pokemon, parent, false)
            .let { PokemonViewHolder(it) }

    override fun getItemCount() = pokemons.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bindItemView(pokemons[position], onItemClick)
    }
}