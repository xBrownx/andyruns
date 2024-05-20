package com.brownx.runningapp.run.presenter.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.brownx.runningapp.R
import com.brownx.runningapp.run.presenter.RunUiEvents
import com.brownx.runningapp.run.presenter.RunViewModel

/**
 * @author Andrew Brown
 * created on 20/05/2024
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RunTopToolBar(
    toolbarTitle: String
) {
    val runViewModel = hiltViewModel<RunViewModel>()
    val runState = runViewModel.runState.collectAsState()

    val lockIcon =
        if(runState.value.isScreenLocked) {
            R.drawable.lock_keyhole_minimalistic_unlocked_svgrepo_com
        } else {
            R.drawable.lock_keyhole_svgrepo_com
        }

    TopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Text(text = toolbarTitle)
        },
        actions = {
            IconButton(
                onClick = {
                    runViewModel.onEvent(
                        RunUiEvents.OnToggleLockScreen
                    )
                }
            ) {
                Icon(
                    painter = painterResource(id = lockIcon),
                    contentDescription = "lock_screen",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}