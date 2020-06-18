package com.hefesto.pokedex.data

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class PokemonJsonDeserializer : JsonDeserializer<Pokemon> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Pokemon {
        val jsonObject = json?.asJsonObject
        val number = jsonObject?.get("id")?.asInt ?: 0
        val name = jsonObject?.get("name")?.asString ?: ""
        val weight = jsonObject?.get("weight")?.asFloat ?: 0f
        val height = jsonObject?.get("height")?.asFloat ?: 0f
        val types = jsonObject?.getAsJsonArray("types")?.map { it.asJsonObject.get("type").asJsonObject.get("name").asString } ?: listOf()
        val imageUrl = jsonObject?.getAsJsonObject("sprites")?.get("front_default")?.asString ?: ""
        return Pokemon(
            0,
            name,
            number,
            types,
            imageUrl,
            weight,
            height,
            0.0,
            0.0
        )
    }
}