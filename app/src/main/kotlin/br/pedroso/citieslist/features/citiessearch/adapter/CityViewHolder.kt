package br.pedroso.citieslist.features.citiessearch.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.pedroso.citieslist.databinding.ItemCityBinding
import br.pedroso.citieslist.domain.entities.City

class CityViewHolder(
    private val binding: ItemCityBinding,
    private val cityOnClickListener: CityOnClickListener
) : RecyclerView.ViewHolder(binding.root) {

    // Since this is a prototype, I'm suppressing this warning as we don't need to care about using
    // string resources for now.
    @SuppressLint("SetTextI18n")
    fun bind(city: City) {
        binding.titleTextView.text = "${city.name} ${getCountryFlagEmoji(city.countryCode)}"
        binding.coordinatesTextView.text =
            "${city.coordinates.latitude}, ${city.coordinates.longitude}"

        binding.root.setOnClickListener {
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
