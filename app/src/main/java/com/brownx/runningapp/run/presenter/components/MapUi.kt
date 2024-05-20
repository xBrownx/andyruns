package com.brownx.runningapp.run.presenter.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.brownx.runningapp.run.presenter.RunViewModel
import com.brownx.runningapp.util.Const
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.rememberCameraPositionState

/**
 * @author Andrew Brown
 * created on 20/05/2024
 */

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MapUi() {

    val runViewModel = hiltViewModel<RunViewModel>()
    val runState by runViewModel.runState.collectAsState()

    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                mapType = MapType.NORMAL,
                isMyLocationEnabled = true,
            )
        )
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(Const.SYDNEY, 18f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
//        uiSettings = mapState.mapUiSettings,
        properties = mapProperties,
        onMapClick = {

        },
        onMapLoaded = {

        },
    ) {
        LaunchedEffect(runState.myLocation) {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newCameraPosition(
                    CameraPosition(runState.myLocation, 18f, 0f, 0f)
                )
            )
            runViewModel.updateDistance()
        }
    }
}