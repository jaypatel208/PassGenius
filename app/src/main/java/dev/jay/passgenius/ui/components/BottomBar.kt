package dev.jay.passgenius.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import dev.jay.passgenius.ui.theme.OrangePrimary
import dev.jay.passgenius.utils.Constants

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
)

val bottomNavigationItems = listOf(
    BottomNavigationItem(
        title = Constants.HOME,
        selectedIcon = Icons.Filled.Home,
        unSelectedIcon = Icons.Outlined.Home
    ),
    BottomNavigationItem(
        title = Constants.SECURITY_AUDIT,
        selectedIcon = Icons.Filled.Assessment,
        unSelectedIcon = Icons.Outlined.Assessment
    ),
    BottomNavigationItem(
        title = Constants.PASSWORD_GENERATE,
        selectedIcon = Icons.Filled.Password,
        unSelectedIcon = Icons.Outlined.Password
    ),
    BottomNavigationItem(
        title = Constants.SETTINGS,
        selectedIcon = Icons.Filled.Settings,
        unSelectedIcon = Icons.Outlined.Settings
    )
)

@Composable
fun BottomBar(navController: NavController) {
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    NavigationBar(containerColor = Color.White) {
        Spacer(Modifier.weight(1f))
        bottomNavigationItems.forEachIndexed { index, bottomNavigationItem ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(bottomNavigationItem.title.lowercase()) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (index == selectedItemIndex)
                            bottomNavigationItem.selectedIcon
                        else
                            bottomNavigationItem.unSelectedIcon,
                        contentDescription = bottomNavigationItem.title,
                        tint = if (index == selectedItemIndex) OrangePrimary else Color.Black
                    )
                },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.White
                )
            )
        }
        Spacer(Modifier.weight(1f))
    }
}