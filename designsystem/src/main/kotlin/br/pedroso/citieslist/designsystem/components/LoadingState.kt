package br.pedroso.citieslist.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.pedroso.citieslist.R

@Composable
fun LoadingState(modifier: Modifier = Modifier) {
    Column(
        modifier.padding(16.dp),
        verticalArrangement = Arrangement.Absolute.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()

        Text(
            text = stringResource(id = R.string.loading),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}
