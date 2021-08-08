package br.pedroso.citieslist.features.citiessearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.pedroso.citieslist.databinding.ItemCityBinding
import br.pedroso.citieslist.domain.entities.City

class CityViewHolder(
    private val binding: ItemCityBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(city: City) {
        binding.titleTextView.text = "${city.name} (${city.countryCode})"
        binding.coordinatesTextView.text =
            "${city.coordinates.latitude}, ${city.coordinates.longitude}"
    }

    companion object {
        fun create(parent: ViewGroup): CityViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemCityBinding.inflate(layoutInflater, parent, false)
            return CityViewHolder(binding)
        }
    }
}
