package dev.jay.passgenius.view.screens

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.jay.passgenius.ui.components.MetricsComponent
import dev.jay.passgenius.ui.components.NoPasswordStoredComponent
import dev.jay.passgenius.ui.components.PasswordsLazyColumn
import dev.jay.passgenius.ui.components.ViewPasswordComponent
import dev.jay.passgenius.ui.theme.OrangePrimary
import dev.jay.passgenius.utils.GeneralUtility
import dev.jay.passgenius.utils.PasswordUtility
import dev.jay.passgenius.viewmodel.HomeScreenViewModel

@Composable
fun HomeScreen(innerPadding: PaddingValues, homeScreenViewModel: HomeScreenViewModel = hiltViewModel()) {
    GeneralUtility.SetStatusBarColor(color = OrangePrimary)
    LaunchedEffect(Unit) {
        homeScreenViewModel.getAllStoredPasswords()
    }
    var itemClicked by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val density = LocalDensity.current
    Box(contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .clickable(
                    onClick = {
                        if (itemClicked) {
                            itemClicked = false
                        }
                    },
                    indication = null,
                    interactionSource = interactionSource
                )
                .blur(if (itemClicked) 7.dp else 0.dp)
                .background(Color.White)
        ) {
            val totalPasswordsSize = homeScreenViewModel.totalPasswordsSize.value
            if (totalPasswordsSize > 0) {
                MetricsComponent(
                    totalPasswords = totalPasswordsSize.toString(),
                    strongPasswords = homeScreenViewModel.strongPasswords.value.size.toString(),
                    mediocrePasswords = homeScreenViewModel.mediocrePasswords.value.size.toString()
                )
                PasswordsLazyColumn(
                    categoriesPasswordStoreModel = PasswordUtility.categorizePasswords(homeScreenViewModel.allStoredPasswords.value),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        .background(Color.White)
                ) { clickedPasswordStoreModel ->
                    homeScreenViewModel.updateClickedPassword(clickedPasswordStoreModel)
                    itemClicked = true
                }
            } else {
                NoPasswordStoredComponent()
            }
        }
        AnimatedVisibility(
            visible = itemClicked,
            enter = slideInVertically { with(density) { 50.dp.roundToPx() } } + fadeIn(
                initialAlpha = 0.3f
            ) + expandVertically(
                expandFrom = Alignment.Top
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()) {
            val password = homeScreenViewModel.clickedPassword.value
            if (password != null) {
                ViewPasswordComponent(
                    siteName = password.site,
                    username = password.username,
                    password = password.password
                )
            }
        }
    }
}