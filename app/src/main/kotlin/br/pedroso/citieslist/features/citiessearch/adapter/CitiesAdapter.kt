package br.pedroso.citieslist.features.citiessearch.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.pedroso.citieslist.domain.entities.City

class CitiesAdapter(
    private val cityOnClickListener: CityOnClickListener,
    private val cities: List<City>,
) : RecyclerView.Adapter<CityViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder =
        CityViewHolder.create(parent, cityOnClickListener)

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cities.getOrNull(position)

        if (city != null) {
            holder.bind(city)
        }
    }

    override fun getItemCount(): Int = cities.size
}