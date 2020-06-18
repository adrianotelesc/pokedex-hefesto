package com.hefesto.pokedex.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PokemonDao {
    @Insert
    fun insert(pokemon: Pokemon)

    @Query("SELECT * FROM pokemon")
    fun selectAll(): List<Pokemon>
}