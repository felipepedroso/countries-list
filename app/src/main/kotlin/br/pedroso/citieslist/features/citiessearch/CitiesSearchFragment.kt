package br.pedroso.citieslist.features.citiessearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import br.pedroso.citieslist.databinding.FragmentCitiesSearchBinding
import br.pedroso.citieslist.features.citiessearch.adapter.CitiesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CitiesSearchFragment : Fragment() {

    private var _binding: FragmentCitiesSearchBinding? = null
    private val binding: FragmentCitiesSearchBinding get() = _binding!!

    private val viewModel by viewModels<CitiesSearchViewModel>()

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
