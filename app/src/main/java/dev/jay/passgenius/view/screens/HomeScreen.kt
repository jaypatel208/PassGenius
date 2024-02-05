package dev.jay.passgenius.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.jay.passgenius.ui.components.MetricsComponent
import dev.jay.passgenius.ui.components.NoPasswordStoredComponent
import dev.jay.passgenius.ui.components.PasswordsLazyColumn
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
    Column(
        modifier = Modifier
            .padding(innerPadding)
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
                PasswordUtility.categorizePasswords(homeScreenViewModel.allStoredPasswords.value),
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                    .background(Color.White)
            )
        } else {
            NoPasswordStoredComponent()
        }
    }
}