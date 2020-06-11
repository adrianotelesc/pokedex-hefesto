package com.hefesto.pokedex

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item_pokemon.view.*

class MainActivity : AppCompatActivity() {

    private var pokemons: List<Pokemon> = listOf(
        Pokemon(
            "Pikachu",
            25,
            listOf("Eletric"),
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png",
            6f,
            4f,
            -3.1028263,
            -60.0147652
        ),
        Pokemon(
            "Squirtle",
            7,
            listOf("Water"),
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png",
            8.5f,
            6f,
            -1.9928572,
            -60.0552653
        ),
        Pokemon(
            "Charmander",
            4,
            listOf("Fire"),
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png",
            9f,
            6f,
            -3.091583,
            -60.0198707
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvPokemons.adapter = PokemonAdapter(pokemons) {
            startActivity(Intent(this, PokemonDetailActivity::class.java))
        }
        shouldDisplayEmptyView(pokemons.isEmpty())
    }

    fun shouldDisplayEmptyView(isEmpty: Boolean) {
        emptyView.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

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

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
            holder.itemView.tvPokemonName.text = pokemons[position].name
            holder.itemView.tvPokemonNumber.text = "#%03d".format(pokemons[position].number)
            Picasso.get().load(pokemons[position].imageUrl).into(holder.itemView.ivPokemonImage)

            holder.itemView.setOnClickListener {
                onItemClick(pokemons[position])
            }
        }
    }
}