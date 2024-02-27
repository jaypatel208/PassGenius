package dev.jay.passgenius.view.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CopyAll
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.jay.passgenius.R
import dev.jay.passgenius.ui.components.CustomAnimatedVisibility
import dev.jay.passgenius.ui.components.MetricsComponent
import dev.jay.passgenius.ui.components.NoPasswordStoredComponent
import dev.jay.passgenius.ui.components.PasswordsLazyColumn
import dev.jay.passgenius.ui.components.ViewPasswordComponent
import dev.jay.passgenius.ui.navigation.Routes
import dev.jay.passgenius.utils.GeneralUtility
import dev.jay.passgenius.utils.PasswordUtility
import dev.jay.passgenius.viewmodel.HomeScreenViewModel

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    snackState: SnackbarHostState,
    navController: NavController,
    onPasswordsChange: (String) -> Unit
) {
    var forceRecomposition by remember {
        mutableStateOf(0)
    }
    LaunchedEffect(forceRecomposition) {
        homeScreenViewModel.getAllStoredPasswords()
    }
    var itemClicked by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
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
                onPasswordsChange(Routes.HOME_SCREEN)
                MetricsComponent(
                    totalPasswords = totalPasswordsSize.toString(),
                    strongPasswords = homeScreenViewModel.strongPasswords.size.toString(),
                    mediocrePasswords = homeScreenViewModel.mediocrePasswords.size.toString()
                )
                PasswordsLazyColumn(
                    categoriesPasswordStoreModel = PasswordUtility.categorizePasswords(homeScreenViewModel.allStoredPasswords),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        .background(Color.White)
                ) { clickedPasswordStoreModel ->
                    homeScreenViewModel.updateClickedPassword(clickedPasswordStoreModel)
                    itemClicked = true
                }
            } else {
                onPasswordsChange(Routes.HOME_SCREEN_NO_PASS)
                NoPasswordStoredComponent()
            }
        }
        CustomAnimatedVisibility(visible = itemClicked) {
            val password = homeScreenViewModel.clickedPassword.value
            if (password != null) {
                val clipboardManager: ClipboardManager = LocalClipboardManager.current
                val context = LocalContext.current
                val coroutineScope = rememberCoroutineScope()
                ViewPasswordComponent(
                    siteName = password.site,
                    username = password.username,
                    password = password.password,
                    onPasswordLongClick = {
                        GeneralUtility.copyTextToClipboard(clipboardManager, password.password)
                        GeneralUtility.showSnackBar(
                            snackState = snackState,
                            coroutineScope = coroutineScope,
                            message = context.getString(R.string.password_copied_simple),
                            imageVector = Icons.Outlined.CopyAll,
                            snackbarDuration = SnackbarDuration.Short
                        )
                    },
                    onUserNameLongClick = {
                        GeneralUtility.copyTextToClipboard(clipboardManager, password.username)
                        GeneralUtility.showSnackBar(
                            snackState = snackState,
                            coroutineScope = coroutineScope,
                            message = context.getString(R.string.username_copied),
                            imageVector = Icons.Outlined.CopyAll,
                            snackbarDuration = SnackbarDuration.Short
                        )
                    },
                    onPasswordClick = {
                        GeneralUtility.showSnackBar(
                            snackState = snackState,
                            coroutineScope = coroutineScope,
                            message = context.getString(R.string.password_copy_info),
                            imageVector = Icons.Outlined.Info,
                            snackbarDuration = SnackbarDuration.Short
                        )
                    },
                    onUserNameClick = {
                        GeneralUtility.showSnackBar(
                            snackState = snackState,
                            coroutineScope = coroutineScope,
                            message = context.getString(R.string.username_copy_info),
                            imageVector = Icons.Outlined.Info,
                            snackbarDuration = SnackbarDuration.Short
                        )
                    },
                    onEditButtonClick = {
                        navController.navigate(
                            route = "${Routes.HomeScreen.EDIT_PASSWORD}/${homeScreenViewModel.clickedPassword.value!!.id}/${homeScreenViewModel.clickedPassword.value!!.site}/${homeScreenViewModel.clickedPassword.value!!.username}/${homeScreenViewModel.clickedPassword.value!!.password}",
                        )
                    },
                    onDeleteButtonClick = {
                        homeScreenViewModel.deletePassword(homeScreenViewModel.clickedPassword.value!!)
                        itemClicked = false
                        forceRecomposition++
                    }
                )
            }
        }
    }

    BackHandler {
        if (itemClicked) {
            itemClicked = false
        } else {
            navController.popBackStack()
        }
    }
}