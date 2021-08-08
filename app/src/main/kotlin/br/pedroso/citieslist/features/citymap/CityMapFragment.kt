package br.pedroso.citieslist.features.citymap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.pedroso.citieslist.R
import br.pedroso.citieslist.databinding.FragmentCityMapBinding

class CityMapFragment : Fragment(R.layout.fragment_city_map) {

    private val navigationArguments: CityMapFragmentArgs by navArgs()

    private var _binding: FragmentCityMapBinding? = null
    private val binding: FragmentCityMapBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title = navigationArguments.city.name
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
