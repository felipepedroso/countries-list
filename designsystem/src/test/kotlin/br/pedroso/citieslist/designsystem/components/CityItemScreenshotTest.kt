package br.pedroso.citieslist.designsystem.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import br.pedroso.citieslist.domain.City
import br.pedroso.citieslist.domain.Coordinates
import org.junit.Test


class CityItemScreenshotTest : ComponentScreenshotTest() {

    @Composable
    private fun CityItemUnderTest(
        name: String = "Bristol",
        isStarred: Boolean = false,
    ) = CityItem(
        modifier = Modifier.fillMaxWidth(),
        city = City(
            name = name,
            countryCode = "GB",
            coordinates = Coordinates(51.4545, -2.5879),
            id = 1,
            isStarred = isStarred,
        ),
    )

    @Test
    fun defaultState() = runScreenshotTest {
        CityItemUnderTest()
    }


    @Test
    fun starredState() = runScreenshotTest {
        CityItemUnderTest(isStarred = true)
    }

    @Test
    fun longName() = runScreenshotTest {
        CityItemUnderTest(name = "Bristol".repeat(10))
    }

    @Test
    fun longNameStarred() = runScreenshotTest {
        CityItemUnderTest(name = "Bristol".repeat(10), isStarred = true)
    }

    @Test
    fun rtl() = runScreenshotTest(layoutDirection = LayoutDirection.Rtl) {
        CityItemUnderTest(isStarred = false)
    }

    @Test
    fun rtlStarred() = runScreenshotTest(layoutDirection = LayoutDirection.Rtl) {
        CityItemUnderTest(isStarred = true)
    }
}