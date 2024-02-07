package dev.jay.passgenius.view.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.jay.passgenius.R
import dev.jay.passgenius.ui.components.PasswordChoiceCard
import dev.jay.passgenius.ui.components.PasswordChoiceText
import dev.jay.passgenius.ui.navigation.Routes
import dev.jay.passgenius.utils.GeneralUtility

@Composable
fun PasswordChoiceScreen(innerPadding: PaddingValues, onBack: () -> Unit, navController: NavController) {
    GeneralUtility.SetStatusBarColor(color = Color.White)
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .background(Color.White),
    ) {
        val robustChoiceClick: () -> Unit = {
            navController.navigate(Routes.PasswordGenerate.PASSWORD_ROBUST_CHOICE)
        }

        val memorableChoiceClick: () -> Unit = {
            navController.navigate(Routes.PasswordGenerate.PASSWORD_MEMORABLE_CHOICE)
        }

        val saveChoiceClick: () -> Unit = {
            navController.navigate(Routes.PasswordGenerate.PASSWORD_SAVE)
        }

        PasswordChoiceText(modifier = Modifier.padding(start = 16.dp, top = 16.dp), fontSize = 36)
        Spacer(modifier = Modifier.height(18.dp))
        PasswordChoiceCard(
            modifier = Modifier.padding(8.dp),
            title = stringResource(id = R.string.robust_security),
            choiceClick = robustChoiceClick
        )
        PasswordChoiceCard(
            modifier = Modifier.padding(8.dp),
            title = stringResource(id = R.string.memorable_strength),
            choiceClick = memorableChoiceClick
        )
        PasswordChoiceCard(
            modifier = Modifier.padding(8.dp),
            title = stringResource(id = R.string.save_password),
            choiceClick = saveChoiceClick
        )
    }
    BackHandler(enabled = true, onBack = onBack)
}