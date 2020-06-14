package com.hefesto.pokedex.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.libraries.places.api.Places
import com.hefesto.pokedex.BuildConfig
import com.hefesto.pokedex.R
import com.hefesto.pokedex.data.Pokemon
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val pokemons: MutableList<Pokemon> = mutableListOf()

    private var adapter: PokemonAdapter = PokemonAdapter(pokemons) {
        Intent(this, PokemonDetailActivity::class.java).apply {
            putExtra(PokemonDetailActivity.POKEMON_EXTRA, it)
        }.also { startActivity(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Places.initialize(applicationContext, BuildConfig.GOOGLE_API_KEY)

        setUpPokemonListWithRecyclerView()
        setUpAddPokemonButtonClick()

        shouldDisplayEmptyView()
    }

    private fun setUpPokemonListWithRecyclerView() {
        rvPokemons.adapter = adapter
    }

    private fun setUpAddPokemonButtonClick() {
        fabAddPokemon.setOnClickListener { startAddPokemonActivityForNewPokemon() }
    }

    private fun startAddPokemonActivityForNewPokemon() {
        Intent(this, AddPokemonActivity::class.java).also {
            startActivityForResult(it, ADD_POKEMON_REQUEST_CODE)
        }
    }

    private fun shouldDisplayEmptyView() {
        emptyView.visibility = if (pokemons.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_POKEMON_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.getParcelableExtra<Pokemon>(AddPokemonActivity.POKEMON_EXTRA)?.let {
                appendNewPokemonToRecyclerView(it)
            }
        }
    }

    private fun appendNewPokemonToRecyclerView(pokemon: Pokemon) {
        pokemons.add(pokemon)
        adapter.notifyItemInserted(pokemons.size - 1)
        shouldDisplayEmptyView()
    }

    companion object {
        const val ADD_POKEMON_REQUEST_CODE = 1
    }

}