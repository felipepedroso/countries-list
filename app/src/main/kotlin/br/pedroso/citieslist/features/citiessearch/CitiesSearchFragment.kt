package br.pedroso.citieslist.features.citiessearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import br.pedroso.citieslist.databinding.FragmentCitiesSearchBinding
import br.pedroso.citieslist.features.citiessearch.CitiesSearchViewEvent.ClickedOnRetry
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
        observeViewState()
    }

    private fun setupRetryButton() {
        binding.errorStateLayout.retryButton.setOnClickListener {
            viewModel.onViewEvent(ClickedOnRetry)
        }
    }

    private fun setupCitiesList() = with(binding.citiesRecyclerView) {
        adapter = CitiesAdapter()
        addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
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

        if (state is DisplayCitiesList) {
            val adapter = citiesRecyclerView.adapter as CitiesAdapter
            adapter.submitList(state.cities)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
