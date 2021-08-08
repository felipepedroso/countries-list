package br.pedroso.citieslist.features.citiessearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import br.pedroso.citieslist.databinding.FragmentCitiesSearchBinding
import br.pedroso.citieslist.features.citiessearch.adapter.CitiesAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCitiesList()
    }

    private fun setupCitiesList() = with(binding.citiesRecyclerView) {
        adapter = CitiesAdapter()
        addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
