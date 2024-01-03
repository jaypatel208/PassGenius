package dev.jay.passgenius.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jay.passgenius.ui.theme.OrangeMetrics

@Composable
fun MetricsComponent() {
    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 3)
            .background(OrangeMetrics)
    ) {
        MetricsText(fontSize = 24)
    }
}

@Composable
fun MetricsText(fontSize: Int) {
    Text(
        modifier = Modifier.padding(8.dp), text = "58", fontSize = fontSize.sp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarApp() {
    TopAppBar(
        title = {
            Text(
//                    text = stringResource(id = R.string.passwords),
                text = "Passwords",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }, actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search",
                    tint = Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = OrangeMetrics)
    )
}