package dev.jay.passgenius.view.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.jay.passgenius.R
import dev.jay.passgenius.database.PasswordModel
import dev.jay.passgenius.ui.components.AddCharacteristicsComponent
import dev.jay.passgenius.ui.components.CopyAndSaveCard
import dev.jay.passgenius.ui.components.CustomAnimatedVisibility
import dev.jay.passgenius.ui.components.MemorablePasswordGenerateText
import dev.jay.passgenius.ui.components.PasswordShowComponent
import dev.jay.passgenius.ui.components.SavePasswordCard
import dev.jay.passgenius.utils.GeneralUtility
import dev.jay.passgenius.viewmodel.MemorablePasswordViewModel

@Composable
fun MemorablePasswordGenerateScreen(
    innerPadding: PaddingValues,
    memorablePasswordViewModel: MemorablePasswordViewModel = hiltViewModel(),
    navController: NavController,
    snackState: SnackbarHostState
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    if (memorablePasswordViewModel.showSnackBar.value) {
        GeneralUtility.showSnackBar(
            snackState = snackState,
            coroutineScope = coroutineScope,
            message = context.getString(R.string.password_copied),
            imageVector = Icons.Outlined.CopyAll,
            snackbarDuration = SnackbarDuration.Short
        )
        memorablePasswordViewModel.updateShowSnackBar(false)
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .blur(if (memorablePasswordViewModel.showCopyAndSaveCard.value || memorablePasswordViewModel.showSavePasswordCard.value) 7.dp else 0.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                MemorablePasswordGenerateText(modifier = Modifier.padding(start = 16.dp, top = 16.dp), fontSize = 36)
                Spacer(modifier = Modifier.height(50.dp))
                AddCharacteristicsComponent(
                    characteristicName = stringResource(id = R.string.words),
                    initialCharacteristicValue = 3,
                    maxCharacteristicValue = 10,
                    onValueChanged = { newWordsValue -> memorablePasswordViewModel.updateWordsValue(newWordsValue) },
                    isMemorable = true
                )
                Spacer(modifier = Modifier.height(50.dp))
                AddCharacteristicsComponent(
                    characteristicName = stringResource(id = R.string.digits),
                    initialCharacteristicValue = 1,
                    maxCharacteristicValue = 5,
                    onValueChanged = { newDigitsValue -> memorablePasswordViewModel.updateDigitsValue(newDigitsValue) },
                    isMemorable = true
                )
            }
            Column {
                PasswordShowComponent(
                    generatedPassword = memorablePasswordViewModel.generatedPassword.value,
                    onRegenerate = { memorablePasswordViewModel.reGeneratePassword() },
                    onDone = { memorablePasswordViewModel.updateShowCopyAndSaveCard(true) })
            }
        }
        CustomAnimatedVisibility(visible = memorablePasswordViewModel.showCopyAndSaveCard.value) {
            val clipboardManager: ClipboardManager = LocalClipboardManager.current
            CopyAndSaveCard(
                generatedPassword = memorablePasswordViewModel.generatedPassword.value,
                onCopyPassword = {
                    memorablePasswordViewModel.updateShowCopyAndSaveCard(false)
                    memorablePasswordViewModel.updateShowSnackBar(true)
                    clipboardManager.setText(AnnotatedString(memorablePasswordViewModel.generatedPassword.value))
                    GeneralUtility.copyTextToClipboard(
                        clipboardManager = clipboardManager,
                        text = memorablePasswordViewModel.generatedPassword.value
                    )
                },
                onSavePassword = {
                    memorablePasswordViewModel.updateShowCopyAndSaveCard(false)
                    memorablePasswordViewModel.updateShowSavePasswordCard(true)
                })
        }
        CustomAnimatedVisibility(visible = memorablePasswordViewModel.showSavePasswordCard.value) {
            SavePasswordCard(generatedPassword = memorablePasswordViewModel.generatedPassword.value) { siteName, userName, generatedPassword ->
                memorablePasswordViewModel.savePassword(
                    PasswordModel(
                        id = 0,
                        site = siteName,
                        username = userName,
                        password = generatedPassword
                    )
                )
                memorablePasswordViewModel.updateShowSavePasswordCard(false)
            }
        }
    }
    BackHandler {
        if (memorablePasswordViewModel.showCopyAndSaveCard.value) {
            memorablePasswordViewModel.updateShowCopyAndSaveCard(false)
        } else {
            navController.popBackStack()
        }
    }
}