package dev.jay.passgenius.view.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CopyAll
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.jay.passgenius.R
import dev.jay.passgenius.di.models.PasswordStoreModel
import dev.jay.passgenius.ui.components.CustomAnimatedVisibility
import dev.jay.passgenius.ui.components.PasswordColumnItem
import dev.jay.passgenius.ui.components.ViewPasswordComponent
import dev.jay.passgenius.ui.navigation.Routes
import dev.jay.passgenius.utils.GeneralUtility
import dev.jay.passgenius.viewmodel.SearchPasswordScreenViewModel

@Composable
fun SearchScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    searchPasswordScreenViewModel: SearchPasswordScreenViewModel = hiltViewModel(),
    snackState: SnackbarHostState
) {
    val searchText by searchPasswordScreenViewModel.searchText.collectAsState()
    val passwords by searchPasswordScreenViewModel.allStoredPassword.collectAsState()
    var itemClicked by remember { mutableStateOf(false) }
    var clickedPassword by remember {
        mutableStateOf(PasswordStoreModel(1, "", "", ""))
    }
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
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
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { newSearch -> searchPasswordScreenViewModel.onSearchTextChange(newSearch) },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search icon", tint = Color.Black)
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Black,
                    focusedBorderColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                placeholder = { Text(text = stringResource(id = R.string.search), color = Color.Black) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (passwords.isEmpty()) {
                Text(text = "Oops!!!", color = Color.Black)
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(passwords) { password ->
                        PasswordColumnItem(
                            passwordStoreModel = password, onPasswordClick = {
                                clickedPassword = password
                                itemClicked = true
                            })
                    }
                }
            }
        }
        CustomAnimatedVisibility(visible = itemClicked) {
            val clipboardManager: ClipboardManager = LocalClipboardManager.current
            val context = LocalContext.current
            val coroutineScope = rememberCoroutineScope()
            ViewPasswordComponent(
                siteName = clickedPassword.site,
                username = clickedPassword.username,
                password = clickedPassword.password,
                onPasswordLongClick = {
                    GeneralUtility.copyTextToClipboard(clipboardManager, clickedPassword.password)
                    GeneralUtility.showSnackBar(
                        snackState = snackState,
                        coroutineScope = coroutineScope,
                        message = context.getString(R.string.password_copied_simple),
                        imageVector = Icons.Outlined.CopyAll,
                        snackbarDuration = SnackbarDuration.Short
                    )
                },
                onUserNameLongClick = {
                    GeneralUtility.copyTextToClipboard(clipboardManager, clickedPassword.username)
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
                        route = "${Routes.HomeScreen.EDIT_PASSWORD}/${clickedPassword.id}/${clickedPassword.site}/${clickedPassword.username}/${clickedPassword.password}",
                    )
                },
                onDeleteButtonClick = {
                    searchPasswordScreenViewModel.deletePassword(clickedPassword)
                    itemClicked = false
                }
            )
        }
    }
}