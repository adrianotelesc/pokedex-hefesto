package com.hefesto.pokedex.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Pokemon(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val number: Int,
    val types: List<String>,
    val imageUrl: String,
    val weight: Float,
    val height: Float,
    var latitude: Double,
    var longitude: Double
) : Parcelable