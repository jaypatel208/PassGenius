package dev.jay.passgenius.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AdsClick
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.jay.passgenius.R
import dev.jay.passgenius.di.models.SnackBarCustom
import dev.jay.passgenius.ui.components.AppTopBar
import dev.jay.passgenius.ui.components.BottomBar
import dev.jay.passgenius.ui.components.CustomAppSnackBar
import dev.jay.passgenius.ui.components.HomeScreenTopBar
import dev.jay.passgenius.ui.navigation.AppNavigationGraph
import dev.jay.passgenius.ui.navigation.Routes
import dev.jay.passgenius.ui.theme.OrangePrimary
import dev.jay.passgenius.ui.theme.PassGeniusTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PassGeniusTheme {
                val navController = rememberNavController()
                val currentScreen = remember { mutableStateOf(Routes.HOME_SCREEN) }
                val showBottomBar = remember { mutableStateOf(true) }
                val showTopBar = remember { mutableStateOf(true) }
                val snackState = remember { SnackbarHostState() }
                val onBack: () -> Unit = {
                    navController.popBackStack()
                }
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    var screen by remember {
                        mutableStateOf(Routes.HOME_SCREEN)
                    }
                    var selectedItemIndex by rememberSaveable {
                        mutableStateOf(0)
                    }
                    val topBarColor = if (screen == Routes.HOME_SCREEN) OrangePrimary else Color.White
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            if (showTopBar.value) {
                                when (currentScreen.value) {
                                    Routes.HOME_SCREEN -> HomeScreenTopBar(
                                        topBarContainerColor = topBarColor,
                                        onSearchClick = { navController.navigate(Routes.HomeScreen.SEARCH) })

                                    Routes.PASSWORD_GENERATE -> AppTopBar(
                                        title = "Select Password Type",
                                        icon = Icons.Outlined.AdsClick,
                                        onBack = onBack
                                    )

                                    Routes.PasswordGenerate.PASSWORD_ROBUST_CHOICE -> AppTopBar(
                                        title = stringResource(id = R.string.robust_password_generate),
                                        icon = Icons.Outlined.Security,
                                        onBack = onBack
                                    )

                                    Routes.PasswordGenerate.PASSWORD_MEMORABLE_CHOICE -> AppTopBar(
                                        title = stringResource(id = R.string.memorable_password_generate),
                                        icon = Icons.Outlined.Psychology,
                                        onBack = onBack
                                    )

                                    Routes.SECURITY_AUDIT -> AppTopBar(
                                        title = stringResource(id = R.string.security_audit),
                                        icon = Icons.Outlined.Analytics,
                                        onBack = onBack
                                    )

                                    Routes.SETTINGS -> AppTopBar(
                                        title = stringResource(id = R.string.settings),
                                        icon = Icons.Outlined.Settings,
                                        onBack = onBack
                                    )
                                    Routes.PasswordGenerate.PASSWORD_SAVE -> AppTopBar(
                                        title = stringResource(id = R.string.save_password),
                                        icon = Icons.Outlined.Save,
                                        onBack = onBack
                                    )

                                    Routes.HomeScreen.EDIT_PASSWORD -> AppTopBar(
                                        title = stringResource(id = R.string.edit_password),
                                        icon = Icons.Outlined.Edit,
                                        onBack = onBack
                                    )
                                }
                            }
                        },
                        bottomBar = {
                            if (showBottomBar.value) {
                                BottomBar(
                                    navController = navController,
                                    selectedItemIndex = selectedItemIndex
                                )
                            }
                        }, snackbarHost = {
                            SnackbarHost(hostState = snackState) {
                                val customVisuals = it.visuals as SnackBarCustom
                                CustomAppSnackBar(
                                    color = Color.White,
                                    imageVector = customVisuals.imageVector,
                                    message = customVisuals.message
                                )
                            }
                        }) { innerPadding ->
                        AppEntryPoint(
                            navController,
                            innerPadding,
                            currentScreen,
                            onBack,
                            showBottomBar,
                            showTopBar,
                            snackState,
                            onScreenChange = { screen = it },
                            onNavigationHappens = { selectedItemIndex = it }
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun AppEntryPoint(
        navHostController: NavHostController,
        innerPadding: PaddingValues,
        currentScreen: MutableState<String>,
        onBack: () -> Unit,
        showBottomBar: MutableState<Boolean>,
        showTopBar: MutableState<Boolean>,
        snackState: SnackbarHostState,
        onScreenChange: (String) -> Unit,
        onNavigationHappens: (Int) -> Unit
    ) {
        AppNavigationGraph(
            navHostController,
            innerPadding,
            currentScreen,
            onBack,
            showBottomBar,
            showTopBar,
            snackState,
            onScreenChange = { givenScreen -> onScreenChange(givenScreen) },
            onNavigationHappens = { onNavigationHappens(it) })
    }
}