package br.pedroso.citieslist.features.citiessearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.pedroso.citieslist.databinding.FragmentCitiesSearchBinding

class CitiesSearchFragment : Fragment() {

    private var _binding: FragmentCitiesSearchBinding? = null
    private val binding: FragmentCitiesSearchBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitiesSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
