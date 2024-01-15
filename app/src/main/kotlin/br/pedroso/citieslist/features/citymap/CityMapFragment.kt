package br.pedroso.citieslist.features.citymap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.pedroso.citieslist.ui.theme.CitiesListTheme

class CityMapFragment : Fragment() {

    private val navigationArguments: CityMapFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                CitiesListTheme {
                    MapScreen(
                        city = navigationArguments.city,
                        onNavigateBack = { findNavController().navigateUp() }
                    )
                }
            }
        }
    }
}
