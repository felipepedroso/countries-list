package br.pedroso.citieslist.features.citiessearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.pedroso.citieslist.R
import br.pedroso.citieslist.databinding.ItemCityBinding
import br.pedroso.citieslist.domain.entities.City
import br.pedroso.citieslist.utils.getCountryFlagEmoji

class CityViewHolder(
    private val binding: ItemCityBinding,
    private val cityOnClickListener: CityOnClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(city: City) = with(binding) {
        titleTextView.text = city.name
        flagTextView.text = getCountryFlagEmoji(city.countryCode)
        countryTextView.text = city.countryCode

        val context = binding.root.context
        val (latitude, longitude) = city.coordinates
        coordinatesTextView.text =
            context.getString(R.string.coordinates_values, latitude, longitude)

        root.setOnClickListener {
            cityOnClickListener.clickedOnCity(city)
        }
    }

    companion object {
        fun create(parent: ViewGroup, cityOnClickListener: CityOnClickListener): CityViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemCityBinding.inflate(layoutInflater, parent, false)
            return CityViewHolder(binding, cityOnClickListener)
        }
    }
}
