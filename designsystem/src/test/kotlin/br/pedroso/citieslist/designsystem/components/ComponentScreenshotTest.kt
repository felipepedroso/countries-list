package br.pedroso.citieslist.designsystem.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.LayoutDirection
import br.pedroso.citieslist.designsystem.theme.CitiesListTheme
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@Config(sdk = [34])
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@RunWith(RobolectricTestRunner::class)
abstract class ComponentScreenshotTest {
    @get:Rule
    val composeRule = createComposeRule()

    fun runScreenshotTest(
        layoutDirection: LayoutDirection = LayoutDirection.Ltr,
        content: @Composable () -> Unit,
    ) {
        composeRule.setContent {
            CitiesListTheme {
                CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                    content()
                }
            }
        }
        composeRule.onRoot().captureRoboImage()
    }
}
