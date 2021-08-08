package br.pedroso.citieslist.features.citymap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import br.pedroso.citieslist.R
import br.pedroso.citieslist.databinding.FragmentCityMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class CityMapFragment : Fragment() {

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
        setupToolbar()
        setupMap()
    }

    private fun setupMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment

        mapFragment.getMapAsync { googleMap ->
            val city = navigationArguments.city
            val (latitude, longitude) = city.coordinates
            val latLng = LatLng(latitude, longitude)
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            googleMap.addMarker(
                MarkerOptions().position(latLng).title(city.name)
            )
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setupWithNavController(findNavController())
        binding.toolbar.title = navigationArguments.city.name
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
