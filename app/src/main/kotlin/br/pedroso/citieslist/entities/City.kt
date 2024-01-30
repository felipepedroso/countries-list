package br.pedroso.citieslist.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
    val name: String,
    val countryCode: String,
    val coordinates: Coordinates,
    val id: Int,
) : Parcelable
