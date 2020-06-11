package com.hefesto.pokedex

data class Pokemon(
    val name: String,
    val number: Int,
    val types: List<String>,
    val imageUrl: String,
    val weight: Float,
    val height: Float,
    val latitude: Double,
    val longitude: Double
)