package dev.jay.passgenius.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Save
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
import dev.jay.passgenius.R
import dev.jay.passgenius.database.PasswordModel
import dev.jay.passgenius.di.models.SnackBarCustom
import dev.jay.passgenius.ui.components.CommonAppButton
import dev.jay.passgenius.ui.components.CommonOutlinedTextField
import dev.jay.passgenius.viewmodel.SavePasswordScreenViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun SavePasswordScreen(
    snackState: SnackbarHostState,
    savePasswordScreenViewModel: SavePasswordScreenViewModel = hiltViewModel()
) {
    var siteName by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isErrorInSiteName by remember { mutableStateOf(false) }
    var isErrorInPassword by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val limitCharSiteName = 30
    val limitCharPassword = 45

    if (savePasswordScreenViewModel.showSnackBar.value) {
        coroutineScope.launch {
            snackState.showSnackbar(
                SnackBarCustom(
                    message = context.getString(R.string.password_saved),
                    imageVector = Icons.Outlined.Save,
                    duration = SnackbarDuration.Short
                )
            )
        }
        savePasswordScreenViewModel.updateShowSnackBar(false)
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
            value = siteName,
            onValueChange = { newSiteName ->
                siteName = newSiteName.take(limitCharSiteName)
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
            value = userName,
            onValueChange = { newUserName ->
                userName = newUserName
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
            value = password,
            onValueChange = { newPassword ->
                validatePassword(newPassword)
                password = newPassword.take(limitCharPassword)
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
                GlobalScope.launch(Dispatchers.IO) {
                    validateSiteName(siteName)
                    validatePassword(password)
                    if (!isErrorInSiteName && !isErrorInPassword) {
                        savePasswordScreenViewModel.savePassword(
                            PasswordModel(
                                id = 0,
                                site = siteName,
                                username = userName,
                                password = password
                            )
                        )
                        siteName = ""
                        userName = ""
                        password = ""
                        savePasswordScreenViewModel.updateShowSnackBar(true)
                    }
                }
            },
            buttonText = stringResource(id = R.string.save),
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}