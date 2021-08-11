package br.pedroso.citieslist.features.citiessearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.pedroso.citieslist.R
import br.pedroso.citieslist.databinding.ItemCityBinding
import br.pedroso.citieslist.domain.entities.City

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

    // This code is based on this StackOverflow question:
    // https://stackoverflow.com/questions/42234666/get-emoji-flag-by-country-code
    private fun getCountryFlagEmoji(countryCode: String): String {
        val firstChar = countryCode[0].code - ASCII_OFFSET + FLAG_OFFSET

        val secondChar = countryCode[1].code - ASCII_OFFSET + FLAG_OFFSET

        return (String(Character.toChars(firstChar)) + String(Character.toChars(secondChar)))
    }

    companion object {
        private const val ASCII_OFFSET = 0x41
        private const val FLAG_OFFSET = 0x1F1E6

        fun create(parent: ViewGroup, cityOnClickListener: CityOnClickListener): CityViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemCityBinding.inflate(layoutInflater, parent, false)
            return CityViewHolder(binding, cityOnClickListener)
        }
    }
}
