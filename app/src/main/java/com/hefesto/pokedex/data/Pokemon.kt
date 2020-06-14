package com.hefesto.pokedex.data

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
    var latitude: Double,
    var longitude: Double
) : Parcelable