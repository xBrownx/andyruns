package com.brownx.runningapp.core.presenter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.brownx.runningapp.R
import com.brownx.runningapp.util.Screen

/**
 * @author Andrew Brown
 * created on 20/05/2024
 */
@Composable
fun AppBottomBar(navController: NavHostController) {

    BottomAppBar(
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                NavigationItem(
                    navController = navController,
                    screen = Screen.Log,
                    icon = painterResource(R.drawable.log_svgrepo_com),
                    label = "Log",
                    scale = 1.4f
                )
                NavigationItem(
                    navController = navController,
                    screen = Screen.Stats,
                    icon = painterResource(R.drawable.stats_up_alt_svgrepo_com),
                    label = "stats",
                    scale = 1.4f
                )
                NavigationItem(
                    navController = navController,
                    screen = Screen.Run,
                    icon = painterResource(R.drawable.running_svgrepo_com),
                    label = "run",
                    scale = 1.75f
                )
                NavigationItem(
                    navController = navController,
                    screen = Screen.Profile,
                    icon = painterResource(R.drawable.profile_svgrepo_com),
                    label = "profile",
                    scale = 1.3f
                )
                NavigationItem(
                    navController = navController,
                    screen = Screen.Settings,
                    icon = painterResource(R.drawable.settings_svgrepo_com),
                    label = "settings",
                    scale = 1.4f
                )
            }
        },
    )
}

@Composable
fun NavigationItem(
    navController: NavHostController,
    screen: Screen,
    icon: Painter,
    label: String,
    scale: Float
) {
    IconButton(
        onClick = { navController.navigate(screen.route) },
        modifier = Modifier.scale(scale),
    ) {
        Icon(
            painter = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,

        )
    }
}