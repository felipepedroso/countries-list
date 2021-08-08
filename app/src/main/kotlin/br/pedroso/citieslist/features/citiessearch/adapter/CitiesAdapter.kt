package br.pedroso.citieslist.features.citiessearch.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import br.pedroso.citieslist.domain.entities.City

class CitiesAdapter(
    private val cityOnClickListener: CityOnClickListener
) : ListAdapter<City, CityViewHolder>(CityItemDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder =
        CityViewHolder.create(parent, cityOnClickListener)

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = getItem(position)

        if (city != null) {
            holder.bind(city)
        }
    }
}
