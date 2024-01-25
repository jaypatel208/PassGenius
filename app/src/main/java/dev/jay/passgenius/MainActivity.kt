package dev.jay.passgenius

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AdsClick
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.jay.passgenius.ui.components.BottomBar
import dev.jay.passgenius.ui.components.HomeScreenTopBar
import dev.jay.passgenius.ui.components.PasswordGenerateScreenTopBar
import dev.jay.passgenius.ui.navigation.AppNavigationGraph
import dev.jay.passgenius.ui.navigation.Routes
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
                val onBack: () -> Unit = {
                    navController.popBackStack()
                }
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            when (currentScreen.value) {
                                Routes.HOME_SCREEN -> HomeScreenTopBar()
                                Routes.PASSWORD_GENERATE -> PasswordGenerateScreenTopBar(
                                    title = "Select Password Type",
                                    icon = Icons.Outlined.AdsClick,
                                    onBack = onBack
                                )
                            }
                        },
                        bottomBar = {
                            if (showBottomBar.value) {
                                BottomBar(navController)
                            }
                        }) { innerPadding ->
                        AppEntryPoint(navController, innerPadding, currentScreen, onBack, showBottomBar)
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
        showBottomBar: MutableState<Boolean>
    ) {
        AppNavigationGraph(navHostController, innerPadding, currentScreen, onBack, showBottomBar)
    }
}