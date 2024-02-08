package dev.jay.passgenius.view.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CopyAll
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.jay.passgenius.R
import dev.jay.passgenius.database.PasswordModel
import dev.jay.passgenius.di.models.SnackBarCustom
import dev.jay.passgenius.ui.components.AddCharacteristicsComponent
import dev.jay.passgenius.ui.components.CharactersComponent
import dev.jay.passgenius.ui.components.CopyAndSaveCard
import dev.jay.passgenius.ui.components.PasswordGenerateText
import dev.jay.passgenius.ui.components.PasswordShowComponent
import dev.jay.passgenius.ui.components.SavePasswordCard
import dev.jay.passgenius.utils.GeneralUtility
import dev.jay.passgenius.viewmodel.RobustPasswordViewModel
import kotlinx.coroutines.launch

@Composable
fun RobustPasswordGenerateScreen(
    innerPadding: PaddingValues,
    robustPasswordViewModel: RobustPasswordViewModel = hiltViewModel(),
    navController: NavController,
    snackState: SnackbarHostState
) {
    GeneralUtility.SetStatusBarColor(color = Color.White)
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    if (robustPasswordViewModel.showSnackBar.value) {
        coroutineScope.launch {
            snackState.showSnackbar(
                SnackBarCustom(
                    message = context.getString(R.string.password_copied),
                    imageVector = Icons.Outlined.CopyAll,
                    duration = SnackbarDuration.Short
                )
            )
        }
        robustPasswordViewModel.updateShowSnackBar(false)
    }
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
                    if (robustPasswordViewModel.showSavePasswordCard.value) robustPasswordViewModel.updateShowSavePasswordCard(
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
        val density = LocalDensity.current
        AnimatedVisibility(
            visible = robustPasswordViewModel.showCopyAndSaveCard.value,
            enter = slideInVertically { with(density) { 100.dp.roundToPx() } } + fadeIn(
                initialAlpha = 0.5f
            ) + expandVertically(
                expandFrom = Alignment.Bottom
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            val clipboardManager: ClipboardManager = LocalClipboardManager.current
            CopyAndSaveCard(
                generatedPassword = robustPasswordViewModel.generatedPassword.value,
                onCopyPassword = {
                    robustPasswordViewModel.updateShowCopyAndSaveCard(false)
                    robustPasswordViewModel.updateShowSnackBar(true)
                    clipboardManager.setText(AnnotatedString(robustPasswordViewModel.generatedPassword.value))
                },
                onSavePassword = {
                    robustPasswordViewModel.updateShowCopyAndSaveCard(
                        false
                    )
                    robustPasswordViewModel.updateShowSavePasswordCard(true)
                }
            )
        }
        AnimatedVisibility(
            visible = robustPasswordViewModel.showSavePasswordCard.value,
            enter = slideInVertically { with(density) { 50.dp.roundToPx() } } + fadeIn(
                initialAlpha = 0.3f
            ) + expandVertically(
                expandFrom = Alignment.Top
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()) {
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

    BackHandler {
        if (robustPasswordViewModel.showSavePasswordCard.value) {
            robustPasswordViewModel.updateShowSavePasswordCard(false)
        } else {
            navController.popBackStack()
        }
    }
}