package com.hefesto.pokedex.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.hefesto.pokedex.R
import com.hefesto.pokedex.data.PokeApi
import com.hefesto.pokedex.data.Pokemon
import com.hefesto.pokedex.isFilled
import com.hefesto.pokedex.shortToast
import kotlinx.android.synthetic.main.activity_add_pokemon.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AddPokemonActivity : AppCompatActivity() {

    private lateinit var selectedPlace: Place

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pokemon)
        setUpLocationInputClick()
        setUpDoneButtonClick()
    }

    private fun setUpLocationInputClick() {
        edtLocationInput.setOnClickListener { startAutocompleteActivityForPlace() }
    }

    private fun startAutocompleteActivityForPlace() {
        val fields = listOf(Place.Field.ADDRESS, Place.Field.LAT_LNG)
        Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this).also {
            startActivityForResult(it, AUTOCOMPLETE_REQUEST_CODE)
        }
    }

    private fun setUpDoneButtonClick() {
        btnDone.setOnClickListener {
            if (edtNameInput.text.isFilled && edtLocationInput.text.isFilled) {
                val name = edtNameInput.text.toString().toLowerCase(Locale.getDefault())
                fetchPokemonByName(
                    name,
                    onSuccess = { returnPokemonAsActivityResult(it) },
                    onError = { shortToast(R.string.error_message) }
                )
            } else {
                shortToast(R.string.invalid_fields_message)
            }
        }
    }

    private fun fetchPokemonByName(
        name: String,
        onSuccess: (Pokemon) -> Unit,
        onError: () -> Unit
    ) {
        PokeApi.INSTANCE.getPokemonByName(name).enqueue(object : Callback<Pokemon> {
            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                onError()
            }

            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                val pokemon = response.body()
                if (response.isSuccessful && pokemon != null) {
                    onSuccess(pokemon)
                } else {
                    onError()
                }
            }
        })
    }

    private fun returnPokemonAsActivityResult(pokemon: Pokemon) {
        val pokemonWithLocation = pokemon.apply {
            latitude = selectedPlace.latLng?.latitude ?: 0.0
            longitude = selectedPlace.latLng?.longitude ?: 0.0
        }
        Intent().apply {
            putExtra(POKEMON_EXTRA, pokemonWithLocation)
        }.also {
            setResult(Activity.RESULT_OK, it)
        }
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {
                selectedPlace = Autocomplete.getPlaceFromIntent(it)
                edtLocationInput.setText(selectedPlace.address)
            }
        }
    }

    companion object {
        const val AUTOCOMPLETE_REQUEST_CODE = 1
        val POKEMON_EXTRA = "${AddPokemonActivity::class.java.`package`}.addPokemon"
    }
}