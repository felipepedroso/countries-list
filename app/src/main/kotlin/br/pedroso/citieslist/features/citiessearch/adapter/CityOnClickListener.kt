package br.pedroso.citieslist.features.citiessearch.adapter

import br.pedroso.citieslist.domain.entities.City

fun interface CityOnClickListener {
    fun clickedOnCity(city: City)
}
