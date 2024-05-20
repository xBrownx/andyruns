package com.brownx.runningapp.run.presenter.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.brownx.runningapp.R
import com.brownx.runningapp.run.presenter.RunState
import com.brownx.runningapp.run.presenter.RunUiEvents
import com.brownx.runningapp.run.presenter.RunViewModel
import com.brownx.runningapp.run.services.TrackingService


/**
 * @author Andrew Brown
 * created on 20/05/2024
 */

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RunDetails() {

    val runViewModel = hiltViewModel<RunViewModel>()
    val runState by runViewModel.runState.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        LeftColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            runState = runState,
        )
        MiddleColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            runViewModel = runViewModel,
            runState = runState,
        )
        RightColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            runState = runState,
        )
    }
}

@Composable
fun LeftColumn(
    modifier: Modifier,
    runState: RunState
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row { Text(text = "Distance") }
        Row { Text(text = "${runState.totalDistance}m") }
        Row { Text(text = "Current Pace") }
        Row { Text(text = "${runState.currentPace}") }
        Row { Text(text = "Average Pace") }
        Row { Text(text = "0'0\"") }
    }
}

@Composable
fun MiddleColumn(
    modifier: Modifier,
    runViewModel: RunViewModel,
    runState: RunState
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row { Text(text = "Warm Up") }
        Row { Text(text = runState.timeRunInMillis) }
        IconButton(
            modifier = Modifier.scale(1.5f),
            onClick = {
                runViewModel.onEvent(
                    RunUiEvents.OnToggleRun
                )
            }
        ) {
            val icon =
                if (runState.isTracking) {
                    R.drawable.pause_circle_svgrepo_com
                } else {
                    R.drawable.play_circle_svgrepo_com
                }
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "play_pause"
            )
        }
    }
}

@Composable
fun RightColumn(
    modifier: Modifier,
    runState: RunState
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row { Text(text = "Next Up") }
    }
}