package dev.jay.passgenius.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.jay.passgenius.utils.Constants
import dev.jay.passgenius.view.screens.EditPasswordScreen
import dev.jay.passgenius.view.screens.HomeScreen
import dev.jay.passgenius.view.screens.MemorablePasswordGenerateScreen
import dev.jay.passgenius.view.screens.PasswordChoiceScreen
import dev.jay.passgenius.view.screens.RobustPasswordGenerateScreen
import dev.jay.passgenius.view.screens.SavePasswordScreen
import dev.jay.passgenius.view.screens.SearchScreen
import dev.jay.passgenius.view.screens.SecurityAuditScreen
import dev.jay.passgenius.view.screens.SettingsScreen

@Composable
fun AppNavigationGraph(
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
    NavHost(navController = navHostController, startDestination = Routes.HOME_SCREEN) {
        composable(route = Routes.HOME_SCREEN) {
            HomeScreen(
                innerPadding = innerPadding,
                snackState = snackState,
                navController = navHostController,
                onPasswordsChange = { currentScreen -> onScreenChange(currentScreen) })
            currentScreen.value = Routes.HOME_SCREEN
            showTopBar.value = true
            showBottomBar.value = true
            onNavigationHappens(0)
        }
        composable(route = Routes.PASSWORD_GENERATE) {
            PasswordChoiceScreen(innerPadding = innerPadding, onBack = onBack, navController = navHostController)
            currentScreen.value = Routes.PASSWORD_GENERATE
            showTopBar.value = true
            showBottomBar.value = true
            onNavigationHappens(2)
        }
        composable(route = Routes.SECURITY_AUDIT) {
            SecurityAuditScreen()
            currentScreen.value = Routes.SECURITY_AUDIT
            showTopBar.value = true
            showBottomBar.value = true
            onNavigationHappens(1)
        }
        composable(route = Routes.SETTINGS) {
            SettingsScreen()
            currentScreen.value = Routes.SETTINGS
            showTopBar.value = true
            showBottomBar.value = true
            onNavigationHappens(3)
        }
        composable(route = Routes.PasswordGenerate.PASSWORD_ROBUST_CHOICE) {
            RobustPasswordGenerateScreen(
                innerPadding = innerPadding,
                navController = navHostController,
                snackState = snackState
            )
            currentScreen.value = Routes.PasswordGenerate.PASSWORD_ROBUST_CHOICE
            showTopBar.value = true
            showBottomBar.value = false
            onNavigationHappens(2)
        }
        composable(route = Routes.PasswordGenerate.PASSWORD_MEMORABLE_CHOICE) {
            MemorablePasswordGenerateScreen(
                innerPadding = innerPadding,
                navController = navHostController,
                snackState = snackState
            )
            currentScreen.value = Routes.PasswordGenerate.PASSWORD_MEMORABLE_CHOICE
            showTopBar.value = true
            showBottomBar.value = false
            onNavigationHappens(2)
        }
        composable(route = Routes.PasswordGenerate.PASSWORD_SAVE) {
            SavePasswordScreen(snackState = snackState)
            currentScreen.value = Routes.PasswordGenerate.PASSWORD_SAVE
            showTopBar.value = true
            showBottomBar.value = false
            onNavigationHappens(2)
        }
        composable(
            route = "${Routes.HomeScreen.EDIT_PASSWORD}/{${Constants.NavArgs.PASSWORD_ID}}/{${Constants.NavArgs.SITE_NAME}}/{${Constants.NavArgs.USER_NAME}}/{${Constants.NavArgs.PASSWORD}}",
            arguments = listOf(
                navArgument(Constants.NavArgs.PASSWORD_ID) { type = NavType.IntType },
                navArgument(Constants.NavArgs.SITE_NAME) { type = NavType.StringType },
                navArgument(Constants.NavArgs.USER_NAME) { type = NavType.StringType },
                navArgument(Constants.NavArgs.PASSWORD) { type = NavType.StringType }
            )
        ) {
            val passwordIndex = it.arguments!!.getInt(Constants.NavArgs.PASSWORD_ID)
            val siteName = it.arguments!!.getString(Constants.NavArgs.SITE_NAME)
            val userName = it.arguments!!.getString(Constants.NavArgs.USER_NAME)
            val password = it.arguments!!.getString(Constants.NavArgs.PASSWORD)
            EditPasswordScreen(
                navController = navHostController,
                passwordID = passwordIndex,
                siteName = siteName!!,
                userName = userName!!,
                password = password!!,
                snackState = snackState
            )
            currentScreen.value = Routes.HomeScreen.EDIT_PASSWORD
            showTopBar.value = true
            showBottomBar.value = false
            onNavigationHappens(0)
        }
        composable(route = Routes.HomeScreen.SEARCH) {
            SearchScreen(innerPadding = innerPadding, navController = navHostController, snackState = snackState)
            currentScreen.value = Routes.HomeScreen.SEARCH
            showTopBar.value = false
            showBottomBar.value = false
        }
    }
}