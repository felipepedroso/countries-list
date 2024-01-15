package br.pedroso.citieslist.features.citiessearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.pedroso.citieslist.ui.theme.CitiesListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CitiesSearchFragment : Fragment() {
    private val viewModel by viewModels<CitiesSearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                CitiesListTheme {
                    CitiesSearchScreen(
                        modifier = Modifier.fillMaxSize(),
                        viewModel = viewModel,
                        openCityOnMap = {
                            findNavController().navigate(
                                CitiesSearchFragmentDirections.openCityMapFragment(it)
                            )
                        }
                    )
                }
            }
        }
    }
}
