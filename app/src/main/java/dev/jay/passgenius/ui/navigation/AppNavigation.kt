package dev.jay.passgenius.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.jay.passgenius.ui.screens.HomeScreen
import dev.jay.passgenius.ui.screens.PasswordChoiceScreen
import dev.jay.passgenius.ui.screens.SecurityAuditScreen
import dev.jay.passgenius.ui.screens.SettingsScreen

@Composable
fun AppNavigationGraph(
    navHostController: NavHostController,
    innerPadding: PaddingValues,
    currentScreen: MutableState<String>,
    onBack: () -> Unit,
    showBottomBar: MutableState<Boolean>
) {
    NavHost(navController = navHostController, startDestination = Routes.HOME_SCREEN) {
        composable(Routes.HOME_SCREEN) {
            HomeScreen(innerPadding)
            currentScreen.value = Routes.HOME_SCREEN
            showBottomBar.value = true
        }
        composable(Routes.PASSWORD_GENERATE) {
            PasswordChoiceScreen(innerPadding, onBack)
            currentScreen.value = Routes.PASSWORD_GENERATE
            showBottomBar.value = true
        }
        composable(Routes.SECURITY_AUDIT) {
            SecurityAuditScreen()
            currentScreen.value = Routes.SECURITY_AUDIT
            showBottomBar.value = true
        }
        composable(Routes.SETTINGS) {
            SettingsScreen()
            currentScreen.value = Routes.SETTINGS
            showBottomBar.value = false
        }
    }
}