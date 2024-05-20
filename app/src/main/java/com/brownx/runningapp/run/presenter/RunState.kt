package com.brownx.runningapp.run.presenter

import com.brownx.runningapp.run.services.Polylines
import com.brownx.runningapp.util.Const.SYDNEY
import com.google.android.gms.maps.model.LatLng

/**
 * @author Andrew Brown
 * created on 20/05/2024
 */

data class RunState(

    val isLoading: Boolean = false,
    val isScreenLocked: Boolean = false,
    val isTracking: Boolean =  false,

    val totalDistance: Int = 0,
    val currentPace: Float = 0f,
    val avgPace: Float = 0f,

    val timeRunInMillis: String = "",
    val currentIntervalActivity: String = "",
    val totalTimeRemainingInMillis: Long = 0L,
    val intervalTimeRemainingInMillis: Long = 0L,

    val myLocation: LatLng = SYDNEY,
    val pathPoints: Polylines = mutableListOf(),

    val lastTimeStamp: Long = 0L,

)