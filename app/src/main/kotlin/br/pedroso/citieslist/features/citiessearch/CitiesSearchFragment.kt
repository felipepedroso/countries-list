package br.pedroso.citieslist.features.citiessearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import br.pedroso.citieslist.R
import br.pedroso.citieslist.databinding.FragmentCitiesSearchBinding
import br.pedroso.citieslist.domain.entities.City
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewEvent.ClickedOnCity
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewEvent.ClickedOnRetry
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewEvent.SearchQueryChanged
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewModelEvent.NavigateToMapScreen
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewState.DisplayCitiesList
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewState.Empty
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewState.Error
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewState.Loading
import br.pedroso.citieslist.features.citiessearch.adapter.CitiesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
        setupRetryButton()
        setupToolbar()
        observeViewState()
        observeViewModelEvents()
    }

    private fun setupToolbar() {
        binding.toolbar.inflateMenu(R.menu.menu)

        binding.toolbar.menu?.let { menu ->
            val searchItem = menu.findItem(R.id.search)
            val searchView = searchItem?.actionView as? SearchView

            if (searchView != null) {
                searchView.queryHint = getString(R.string.search)
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean = false

                    override fun onQueryTextChange(newText: String?): Boolean {
                        viewModel.onViewEvent(SearchQueryChanged(newText.orEmpty()))
                        return true
                    }
                })
            }
        }
    }

    private fun observeViewModelEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewModelEventFlow.collect { event -> handleViewModelEvent(event) }
            }
        }
    }

    private fun handleViewModelEvent(event: CitiesSearchViewModelEvent) {
        when (event) {
            is NavigateToMapScreen -> findNavController().navigate(
                CitiesSearchFragmentDirections.openCityMapFragment(event.cityToFocus)
            )
        }
    }

    private fun setupRetryButton() {
        binding.errorStateLayout.retryButton.setOnClickListener {
            viewModel.onViewEvent(ClickedOnRetry)
        }
    }

    private fun setupCitiesList() {
        binding.citiesRecyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        )
    }

    private fun observeViewState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewStateFlow.collect { state -> handleViewState(state) }
            }
        }
    }

    private fun handleViewState(state: CitiesSearchViewState) = with(binding) {
        citiesRecyclerView.isVisible = state is DisplayCitiesList
        emptyLabelTextView.isVisible = state is Empty
        errorStateLayout.root.isVisible = state is Error
        loadingStateLayout.root.isVisible = state is Loading

        when (state) {
            is DisplayCitiesList -> {
                // Recreating the adapter is a subpar solution but ListAdapter was not being
                // able to execute the diff properly for 200k elements.
                // While I tried to fix by changing some parameters of ListAdapter and RecyclerView,
                // I decided to leave the solution out of the scope of this project.
                // Two possible solutions would be introducing some sort of pagination or fixing
                // the thread used by ListAdapter to process the diffs.
                citiesRecyclerView.adapter = CitiesAdapter(::handleClickOnCity, state.cities)

                val itemsCount = state.cities.size
                elementsCounterTextView.text =
                    resources.getQuantityString(R.plurals.query_result, itemsCount, itemsCount)
            }
            Empty -> {
                citiesRecyclerView.adapter = null
                elementsCounterTextView.text =
                    resources.getQuantityString(R.plurals.query_result, 0, 0)
            }
            is Error -> citiesRecyclerView.adapter = null
            Loading -> {
                citiesRecyclerView.adapter = null
                elementsCounterTextView.text = getString(R.string.loading_quantity)
            }
        }
    }

    private fun handleClickOnCity(city: City) = viewModel.onViewEvent(ClickedOnCity(city))

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
