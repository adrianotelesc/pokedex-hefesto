package com.hefesto.pokedex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_pokemon.view.*

class PokemonAdapter(
    private val pokemons: List<Pokemon>,
    private val onItemClick: (Pokemon) -> Unit
) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {
    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_pokemon, parent, false)
        return PokemonViewHolder(itemView)
    }

    override fun getItemCount() = pokemons.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.itemView.tvName.text = pokemons[position].name
        holder.itemView.tvNumber.text = holder.itemView.tvNumber.context.getString(R.string.pokemon_number_format).format(pokemons[position].number)
        Picasso.get().load(pokemons[position].imageUrl).into(holder.itemView.ivImage)

        holder.itemView.setOnClickListener {
            onItemClick(pokemons[position])
        }
    }
}