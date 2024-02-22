package dev.jay.passgenius.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.jay.passgenius.view.screens.HomeScreen
import dev.jay.passgenius.view.screens.MemorablePasswordGenerateScreen
import dev.jay.passgenius.view.screens.PasswordChoiceScreen
import dev.jay.passgenius.view.screens.RobustPasswordGenerateScreen
import dev.jay.passgenius.view.screens.SavePasswordScreen
import dev.jay.passgenius.view.screens.SecurityAuditScreen
import dev.jay.passgenius.view.screens.SettingsScreen

@Composable
fun AppNavigationGraph(
    navHostController: NavHostController,
    innerPadding: PaddingValues,
    currentScreen: MutableState<String>,
    onBack: () -> Unit,
    showBottomBar: MutableState<Boolean>,
    snackState: SnackbarHostState,
    onScreenChange: (String) -> Unit,
    onNavigationHappens: (Int) -> Unit
) {
    NavHost(navController = navHostController, startDestination = Routes.HOME_SCREEN) {
        composable(Routes.HOME_SCREEN) {
            HomeScreen(
                innerPadding = innerPadding,
                snackState = snackState,
                navController = navHostController,
                onPasswordsChange = { currentScreen -> onScreenChange(currentScreen) })
            currentScreen.value = Routes.HOME_SCREEN
            showBottomBar.value = true
            onNavigationHappens(0)
        }
        composable(Routes.PASSWORD_GENERATE) {
            PasswordChoiceScreen(innerPadding = innerPadding, onBack = onBack, navController = navHostController)
            currentScreen.value = Routes.PASSWORD_GENERATE
            showBottomBar.value = true
            onNavigationHappens(2)
        }
        composable(Routes.SECURITY_AUDIT) {
            SecurityAuditScreen()
            currentScreen.value = Routes.SECURITY_AUDIT
            showBottomBar.value = true
            onNavigationHappens(1)
        }
        composable(Routes.SETTINGS) {
            SettingsScreen()
            currentScreen.value = Routes.SETTINGS
            showBottomBar.value = true
            onNavigationHappens(3)
        }
        composable(Routes.PasswordGenerate.PASSWORD_ROBUST_CHOICE) {
            RobustPasswordGenerateScreen(
                innerPadding = innerPadding,
                navController = navHostController,
                snackState = snackState
            )
            currentScreen.value = Routes.PasswordGenerate.PASSWORD_ROBUST_CHOICE
            showBottomBar.value = false
            onNavigationHappens(2)
        }
        composable(Routes.PasswordGenerate.PASSWORD_MEMORABLE_CHOICE) {
            MemorablePasswordGenerateScreen(
                innerPadding = innerPadding,
                navController = navHostController,
                snackState = snackState
            )
            currentScreen.value = Routes.PasswordGenerate.PASSWORD_MEMORABLE_CHOICE
            showBottomBar.value = false
            onNavigationHappens(2)
        }
        composable(Routes.PasswordGenerate.PASSWORD_SAVE) {
            SavePasswordScreen(snackState = snackState)
            currentScreen.value = Routes.PasswordGenerate.PASSWORD_SAVE
            showBottomBar.value = false
            onNavigationHappens(2)
        }
    }
}