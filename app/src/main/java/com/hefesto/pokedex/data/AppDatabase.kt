package com.hefesto.pokedex.data

import android.content.Context
import androidx.room.*

@Database(entities = [Pokemon::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract val pokemonDao: PokemonDao

    companion object {
        fun getInstance(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "pokedex-db")
                .allowMainThreadQueries() // nao usa isso em producao, por favor. amem e blau!
                .build()
    }
}