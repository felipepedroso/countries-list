package br.pedroso.citieslist.entities

import android.os.Parcelable
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize

@Parcelize
@Stable
data class City(
    val name: String,
    val countryCode: String,
    val coordinates: Coordinates,
    val id: Int,
    val isStarred: Boolean,
) : Parcelable
