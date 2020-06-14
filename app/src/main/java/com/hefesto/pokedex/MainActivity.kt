package com.hefesto.pokedex

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.libraries.places.api.Places
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item_pokemon.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: PokemonAdapter

    private var pokemons: MutableList<Pokemon> = mutableListOf(
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
        Places.initialize(applicationContext, BuildConfig.GOOGLE_API_KEY)
        setUpRecyclerView()
        fabAddPokemon.setOnClickListener {
            val intent = Intent(this, AddPokemonActivity::class.java)
            startActivityForResult(intent, ADD_POKEMON_REQUEST_CODE)
        }
        shouldDisplayEmptyView(pokemons.isEmpty())
    }

    private fun setUpRecyclerView() {
        adapter = PokemonAdapter(pokemons) {
            val intent = Intent(this, PokemonDetailActivity::class.java).apply {
                putExtra(PokemonDetailActivity.POKEMON_EXTRA, it)
            }
            startActivity(intent)
        }
        rvPokemons.adapter = adapter
    }

    private fun shouldDisplayEmptyView(isEmpty: Boolean) {
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

        override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
            holder.itemView.tvName.text = pokemons[position].name
            holder.itemView.tvNumber.text =
                holder.itemView.tvNumber.context.getString(R.string.pokemon_number_format)
                    .format(pokemons[position].number)
            Picasso.get().load(pokemons[position].imageUrl).into(holder.itemView.ivImage)

            holder.itemView.setOnClickListener {
                onItemClick(pokemons[position])
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_POKEMON_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.getParcelableExtra<Pokemon>(AddPokemonActivity.ADD_POKEMON_EXTRA)?.let {
                pokemons.add(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

    companion object {
        const val ADD_POKEMON_REQUEST_CODE = 1
    }

}