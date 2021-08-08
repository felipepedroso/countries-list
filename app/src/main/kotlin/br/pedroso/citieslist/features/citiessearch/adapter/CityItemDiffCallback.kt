package br.pedroso.citieslist.features.citiessearch.adapter

import androidx.recyclerview.widget.DiffUtil
import br.pedroso.citieslist.domain.entities.City

object CityItemDiffCallback : DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean =
        oldItem == newItem
}
