package com.example.sportassistant.presentation.home.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sportassistant.R
import com.example.sportassistant.presentation.HomeNavGraph
import com.example.sportassistant.presentation.HomeRoutes
import com.example.sportassistant.presentation.Route

@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    logout: () -> Unit,
) {
    BackHandler {  }
    Scaffold (
        bottomBar = {
            getBottomBar(
                navController = navController,
                onTabPressed = { it: Route ->
                    navController.navigate(it.route)
                }
            )
        },
        topBar = {
            getTopBar(
                navController = navController,
                onHomeClick = {
                    navController.navigate(HomeRoutes.Home.route)
                },
            )
        },
        modifier = modifier
    ) { padding ->
        HomeNavGraph(
            navController = navController,
            logout = logout,
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
private fun getBottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onTabPressed: ((Route) -> Unit),
) {
    val screensWithBottomBar = listOf(
        HomeRoutes.Home.route,
        HomeRoutes.Pinned.route,
        HomeRoutes.Calendar.route,
        HomeRoutes.Settings.route,
        HomeRoutes.Profile.route,
    )
    val navigationItemContentList = listOf(
        NavigationItemContent(
            icon = R.drawable.profile,
            text = stringResource(id = R.string.nav_bar_profile_text),
            route = HomeRoutes.Profile,
        ),
        NavigationItemContent(
            icon = R.drawable.settings,
            text = stringResource(id = R.string.nav_bar_settings_text),
            route = HomeRoutes.Settings,
        ),
        NavigationItemContent(
            icon = R.drawable.calendar,
            text = stringResource(id = R.string.nav_bar_calendar_text),
            route = HomeRoutes.Calendar,
        ),
        NavigationItemContent(
            icon = R.drawable.pin,
            text = stringResource(id = R.string.nav_bar_pinned_text),
            route = HomeRoutes.Pinned,
        )
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    if (screensWithBottomBar.contains(currentDestination?.route)) {
        NavigationBar(
            modifier = modifier,
            containerColor = Color.Transparent
        ) {
            for (navItem in navigationItemContentList) {
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == navItem.route.route } == true,
                    onClick = { onTabPressed(navItem.route) },
                    icon = {
                        Icon(
                            painter = painterResource(navItem.icon),
                            contentDescription = navItem.text
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = Color.Transparent,
                    ),
                    label = { Text(text = navItem.text) },
                )
            }
        }
    }
}

private data class NavigationItemContent(
    val icon: Int,
    val text: String,
    val route: Route,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun getTopBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onHomeClick: () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val screensWithBottomBar = listOf(
        HomeRoutes.Pinned.route,
        HomeRoutes.Calendar.route,
        HomeRoutes.Settings.route,
        HomeRoutes.Profile.route,
    )
    if (screensWithBottomBar.contains(currentDestination?.route)) {

        val currentScreen = getTopBarTitles(
            route = currentDestination?.route ?: ""
        )

        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = currentScreen,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            modifier = modifier,
            navigationIcon = {
                Row(
                    modifier = Modifier
                        .clickable { onHomeClick() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.chevron_left),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(R.string.nav_bar_main_text),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 17.sp
                        )
                    )
                }
            }
        )
    }
}

@Composable
private fun getTopBarTitles(route: String): String {
    val title = when (route) {
        HomeRoutes.Pinned.route -> stringResource(R.string.nav_bar_pinned_text)
        HomeRoutes.Profile.route -> stringResource(R.string.nav_bar_profile_text)
        HomeRoutes.Settings.route -> stringResource(R.string.nav_bar_settings_text)
        HomeRoutes.Calendar.route -> stringResource(R.string.nav_bar_calendar_text)
        else -> "ERROR"
    }
    return title
}
