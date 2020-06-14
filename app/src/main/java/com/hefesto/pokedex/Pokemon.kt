package com.hefesto.pokedex

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
    val name: String,
    val number: Int,
    val types: List<String>,
    val imageUrl: String,
    val weight: Float,
    val height: Float,
    val latitude: Double,
    val longitude: Double
) : Parcelable