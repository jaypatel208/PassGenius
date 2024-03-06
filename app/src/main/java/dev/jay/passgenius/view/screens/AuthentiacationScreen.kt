@file:OptIn(ExperimentalMaterial3Api::class)

package dev.jay.passgenius.view.screens

import android.view.Window
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.jay.passgenius.R
import dev.jay.passgenius.ui.components.BottomSheet
import dev.jay.passgenius.ui.components.FilledButton
import dev.jay.passgenius.ui.components.InfoDialog
import dev.jay.passgenius.ui.components.NumberPad
import dev.jay.passgenius.ui.components.PinIndicatorFingerPrint
import dev.jay.passgenius.ui.components.PinIndicatorStar
import dev.jay.passgenius.ui.navigation.Routes
import dev.jay.passgenius.ui.theme.OrangePrimary
import dev.jay.passgenius.utils.Constants
import dev.jay.passgenius.utils.GeneralUtility
import dev.jay.passgenius.utils.PreferencesManager
import dev.jay.passgenius.viewmodel.AuthenticationScreenViewModel

@Composable
fun AuthenticationScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    window: Window,
    authenticationScreenViewModel: AuthenticationScreenViewModel = hiltViewModel()
) {
    GeneralUtility.SetStatusBarColor(color = Color.White)

    LaunchedEffect(key1 = true) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    val context = LocalContext.current

    val preferencesManager = remember { PreferencesManager(context) }
    val authStatus = remember {
        mutableStateOf(
            preferencesManager.getData(Constants.Preferences.AUTH_STATUS, Constants.Preferences.DEFAULT_VAL)
        )
    }
    val userPin = remember {
        mutableStateOf(
            preferencesManager.getData(Constants.Preferences.PIN, Constants.Preferences.DEFAULT_VAL)
        )
    }

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    val biometricManager = remember { BiometricManager.from(context) }

    val isBiometricAvailable = remember {
        biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
    }

    var isBiometricUsable by remember {
        mutableStateOf(false)
    }

    var bioMetricStatus by remember {
        mutableStateOf("")
    }

    val executor = remember {
        ContextCompat.getMainExecutor(context)
    }

    var openBiometricWarningDialog by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_bg),
            contentDescription = "login bg",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.05f
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (authStatus.value == Constants.Preferences.AUTH_TRUE) {
                when (isBiometricAvailable) {
                    BiometricManager.BIOMETRIC_SUCCESS -> {
                        isBiometricUsable = true
                    }

                    BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                        bioMetricStatus = stringResource(id = R.string.no_biometric_not_available)
                    }

                    BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                        bioMetricStatus = stringResource(id = R.string.no_biometric_unavailable)
                    }

                    BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                        bioMetricStatus = stringResource(id = R.string.no_biometric_vulnerability)
                    }

                    BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                        bioMetricStatus = stringResource(id = R.string.no_biometric_incompatible)
                    }

                    BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                        bioMetricStatus = stringResource(id = R.string.no_biometric_unable_to_determine)
                    }

                    BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                        bioMetricStatus = stringResource(id = R.string.no_biometric_credential_enrolled)
                    }
                }

                val biometricPrompt = BiometricPrompt(
                    context as FragmentActivity,
                    executor,
                    object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                            super.onAuthenticationError(errorCode, errString)
                            if (errString == context.getString(R.string.use_pin)) {
                                showBottomSheet = true
                            }
                        }

                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                            super.onAuthenticationSucceeded(result)
                            navController.popBackStack()
                            navController.navigate(Routes.HOME_SCREEN) {
                                launchSingleTop = true
                            }
                        }
                    })

                val promptInfo =
                    BiometricPrompt.PromptInfo.Builder()
                        .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                        .setTitle(stringResource(id = R.string.bio_auth))
                        .setSubtitle(stringResource(id = R.string.bio_auth_info))
                        .setNegativeButtonText(stringResource(id = R.string.use_pin))
                        .build()
                Text(
                    text = stringResource(id = R.string.welcome_back),
                    color = OrangePrimary,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = stringResource(id = R.string.login_pin_biometric), color = OrangePrimary, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(24.dp))
                PinIndicatorFingerPrint(onEnterPinClick = { showBottomSheet = true }, onFingerPrintIconClick = {
                    if (isBiometricUsable) {
                        biometricPrompt.authenticate(promptInfo)
                    } else {
                        openBiometricWarningDialog = true
                    }
                })
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.welcome),
                            color = OrangePrimary,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = stringResource(id = R.string.pin_instruction),
                            textAlign = TextAlign.Center,
                            color = OrangePrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(48.dp))
                        PinIndicatorStar(authenticationScreenViewModel.clickedButtons)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        NumberPad(onButtonClick = { buttonNumber ->
                            authenticationScreenViewModel.addClickedButton(
                                buttonNumber.toInt()
                            )
                        },
                            onBackSpaceClick = { authenticationScreenViewModel.deleteLastElement() },
                            onClearClick = { authenticationScreenViewModel.clearList() })
                        Spacer(modifier = Modifier.height(24.dp))
                        FilledButton(
                            text = stringResource(id = R.string.done),
                            onButtonClick = {
                                if (authenticationScreenViewModel.clickedButtons.none { it == -1 }) {
                                    preferencesManager.saveData(
                                        Constants.Preferences.AUTH_STATUS,
                                        Constants.Preferences.AUTH_TRUE
                                    )
                                    preferencesManager.saveData(
                                        Constants.Preferences.PIN,
                                        authenticationScreenViewModel.getUserPin()
                                    )
                                    navController.navigate(Routes.HOME_SCREEN)
                                }
                            })
                    }
                }
            }
        }
    }
    if (openBiometricWarningDialog) {
        InfoDialog(
            title = stringResource(id = R.string.cant_use_biometrics),
            info = bioMetricStatus,
            buttonText = stringResource(
                id = R.string.ok
            )
        ) {
            openBiometricWarningDialog = false
        }
    }
    if (showBottomSheet) {
        LaunchedEffect(key1 = authenticationScreenViewModel.pinEntered.value) {
            if (authenticationScreenViewModel.pinEntered.value) {
                val enteredPin = authenticationScreenViewModel.getUserPin()
                if (userPin.value == enteredPin) {
                    showBottomSheet = false
                    navController.popBackStack()
                    navController.navigate(Routes.HOME_SCREEN) {
                        launchSingleTop = true
                    }
                } else {
                    authenticationScreenViewModel.clearList()
                }
            }
        }
        BottomSheet(
            pinIndicationList = authenticationScreenViewModel.clickedButtons,
            sheetState = sheetState,
            onSheet = { boolean -> showBottomSheet = boolean },
            onButtonClick = { buttonNumber -> authenticationScreenViewModel.addClickedButton(buttonNumber.toInt()) },
            onBackSpaceClick = { authenticationScreenViewModel.deleteLastElement() },
            onClearClick = { authenticationScreenViewModel.clearList() })
    }
}
