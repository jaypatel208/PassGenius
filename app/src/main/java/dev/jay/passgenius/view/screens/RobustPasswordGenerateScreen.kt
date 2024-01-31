package dev.jay.passgenius.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.jay.passgenius.R
import dev.jay.passgenius.database.PasswordModel
import dev.jay.passgenius.ui.components.AddCharacteristicsComponent
import dev.jay.passgenius.ui.components.CharactersComponent
import dev.jay.passgenius.ui.components.CopyAndSaveCard
import dev.jay.passgenius.ui.components.PasswordGenerateText
import dev.jay.passgenius.ui.components.PasswordShowComponent
import dev.jay.passgenius.ui.components.SavePasswordCard
import dev.jay.passgenius.utils.GeneralUtility
import dev.jay.passgenius.viewmodel.RobustPasswordViewModel

@Composable
fun RobustPasswordGenerateScreen(
    innerPadding: PaddingValues,
    robustPasswordViewModel: RobustPasswordViewModel = hiltViewModel()
) {
    GeneralUtility.SetStatusBarColor(color = Color.White)
    Box(contentAlignment = Alignment.Center) {
        val interactionSource = remember { MutableInteractionSource() }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .blur(if (robustPasswordViewModel.showCopyAndSaveCard.value || robustPasswordViewModel.showSavePasswordCard.value) 7.dp else 0.dp)
                .clickable(onClick = {
                    if (robustPasswordViewModel.showCopyAndSaveCard.value) robustPasswordViewModel.updateShowCopyAndSaveCard(
                        false
                    )
                }, indication = null, interactionSource = interactionSource)
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
            PasswordShowComponent(
                generatedPassword = robustPasswordViewModel.generatedPassword.value,
                onRegenerate = { robustPasswordViewModel.regeneratePassword() },
                onDone = { robustPasswordViewModel.updateShowCopyAndSaveCard(true) }
            )
        }
        if (robustPasswordViewModel.showCopyAndSaveCard.value) {
            CopyAndSaveCard(
                generatedPassword = robustPasswordViewModel.generatedPassword.value,
                context = LocalContext.current,
                onSavePassword = {
                    robustPasswordViewModel.updateShowCopyAndSaveCard(
                        false
                    )
                    robustPasswordViewModel.updateShowSavePasswordCard(true)
                }
            )
        }
        if (robustPasswordViewModel.showSavePasswordCard.value) {
            SavePasswordCard(
                generatedPassword = robustPasswordViewModel.generatedPassword.value,
                onSavePassword = { siteName, userName, generatedPassword ->
                    robustPasswordViewModel.savePassword(
                        PasswordModel(id = 0, site = siteName, username = userName, password = generatedPassword)
                    )
                    robustPasswordViewModel.updateShowSavePasswordCard(false)
                })
        }
    }
}