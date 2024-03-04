package dev.jay.passgenius.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Update
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.jay.passgenius.R
import dev.jay.passgenius.database.PasswordModel
import dev.jay.passgenius.di.models.SnackBarCustom
import dev.jay.passgenius.ui.components.CommonAppButton
import dev.jay.passgenius.ui.components.CommonOutlinedTextField
import dev.jay.passgenius.utils.Constants
import dev.jay.passgenius.viewmodel.EditPasswordScreenViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun EditPasswordScreen(
    navController: NavController,
    passwordID: Int,
    siteName: String,
    userName: String,
    password: String,
    snackState: SnackbarHostState,
    editPasswordScreenViewModel: EditPasswordScreenViewModel = hiltViewModel()
) {
    var siteNameScreen by remember { mutableStateOf(siteName) }
    var userNameScreen by remember { mutableStateOf(userName) }
    var passwordScreen by remember { mutableStateOf(password) }
    var isErrorInSiteName by remember { mutableStateOf(false) }
    var isErrorInPassword by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    if (editPasswordScreenViewModel.showSnackBar.value) {
        coroutineScope.launch {
            snackState.showSnackbar(
                SnackBarCustom(
                    message = context.getString(R.string.password_updated),
                    imageVector = Icons.Outlined.Update,
                    duration = SnackbarDuration.Short
                )
            )
        }
        editPasswordScreenViewModel.updateShowSnackBar(false)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        fun validateSiteName(siteName: String) {
            isErrorInSiteName = siteName.isBlank()
        }

        fun validatePassword(password: String) {
            isErrorInPassword = (password.length <= 3)
        }
        CommonOutlinedTextField(
            value = siteNameScreen,
            onValueChange = { newSiteName ->
                siteNameScreen = newSiteName.take(Constants.LIMIT_CHAR_SITE_NAME)
                validateSiteName(newSiteName)
            },
            placeholder = stringResource(
                id = R.string.enter_site
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 32.dp),
            label = stringResource(id = R.string.site_name),
            isError = isErrorInSiteName,
            error = stringResource(id = R.string.blank_site_name)
        )
        CommonOutlinedTextField(
            value = userNameScreen,
            onValueChange = { newUserName ->
                userNameScreen = newUserName
            },
            placeholder = stringResource(
                id = R.string.enter_username
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 32.dp),
            label = stringResource(id = R.string.user_name),
            isError = false, error = ""
        )
        CommonOutlinedTextField(
            value = passwordScreen,
            onValueChange = { newPassword ->
                validatePassword(newPassword)
                passwordScreen = newPassword.take(Constants.LIMIT_CHAR_PASSWORD)
            },
            placeholder = stringResource(
                id = R.string.enter_password
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 32.dp),
            label = stringResource(id = R.string.password),
            isError = isErrorInPassword,
            error = stringResource(id = R.string.blank_password)
        )
        CommonAppButton(
            onClick = {
                validateSiteName(siteNameScreen)
                validatePassword(passwordScreen)
                if (!isErrorInSiteName && !isErrorInPassword) {
                    editPasswordScreenViewModel.updatePassword(
                        PasswordModel(
                            id = passwordID,
                            site = siteNameScreen,
                            username = userNameScreen,
                            password = passwordScreen
                        )
                    )
                    navController.popBackStack()
                }
            },
            buttonText = stringResource(id = R.string.update_password),
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}