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

    val totalDistanceInMetres: Int = 0,
    val currentPaceInMinutesPerKm: String = "00'00\"",
    val avgPaceInMinutesPerKm: String = "00'00\"",

    val totalRunDurationInMillis: String = "",
    val totalTimeRunInMillis: String = "",

    val intervalActivity: String = "",
    val intervalDurationInMillis: Long = 360000L, // 5 minutes
    val intervalTimeRemainingInMillis: Long = 0L,
    val intervalProgress: Float = 1.00f,

    val myLocation: LatLng = SYDNEY,
    val pathPoints: Polylines = mutableListOf(),

    val lastTimeStamp: Long = 0L,




)