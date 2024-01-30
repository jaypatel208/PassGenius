package dev.jay.passgenius.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.jay.passgenius.R
import dev.jay.passgenius.ui.components.AddCharacteristicsComponent
import dev.jay.passgenius.ui.components.CharactersComponent
import dev.jay.passgenius.ui.components.PasswordGenerateText
import dev.jay.passgenius.ui.components.PasswordShowComponent
import dev.jay.passgenius.utils.GeneralUtility
import dev.jay.passgenius.viewmodel.RobustPasswordViewModel

@Composable
fun RobustPasswordGenerateScreen(
    innerPadding: PaddingValues,
    robustPasswordViewModel: RobustPasswordViewModel = hiltViewModel()
) {
    GeneralUtility.SetStatusBarColor(color = Color.White)
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .background(Color.White)
    ) {
        PasswordGenerateText(modifier = Modifier.padding(start = 16.dp, top = 16.dp), fontSize = 36)
        Spacer(modifier = Modifier.height(50.dp))
        CharactersComponent(initialLengthValue = robustPasswordViewModel.lengthValue.value) { newLengthValue ->
            robustPasswordViewModel.updateLengthValue(newLengthValue)
        }
        Spacer(modifier = Modifier.height(30.dp))
        AddCharacteristicsComponent(
            characteristicName = stringResource(id = R.string.digits),
            maxCharacteristicValue = robustPasswordViewModel.maxCharacteristicValue.value,
            initialCharacteristicValue = robustPasswordViewModel.digitsValue.value
        ) { newDigitValue ->
            robustPasswordViewModel.updateDigitsValue(newDigitValue)
        }
        Spacer(modifier = Modifier.height(30.dp))
        AddCharacteristicsComponent(
            characteristicName = stringResource(id = R.string.capitals),
            maxCharacteristicValue = robustPasswordViewModel.maxCharacteristicValue.value,
            initialCharacteristicValue = robustPasswordViewModel.capitalValue.value
        ) { newCapitalsValue ->
            robustPasswordViewModel.updateCapitalValue(newCapitalsValue)
        }
        Spacer(modifier = Modifier.height(30.dp))
        AddCharacteristicsComponent(
            characteristicName = stringResource(id = R.string.symbols),
            maxCharacteristicValue = robustPasswordViewModel.maxCharacteristicValue.value,
            initialCharacteristicValue = robustPasswordViewModel.symbolsValue.value
        ) { newSymbolsValue ->
            robustPasswordViewModel.updateSymbolsValue(newSymbolsValue)
        }
        Spacer(modifier = Modifier.height(50.dp))
        PasswordShowComponent(robustPasswordViewModel.generatedPassword.value) {
            robustPasswordViewModel.regeneratePassword()
        }
    }
}