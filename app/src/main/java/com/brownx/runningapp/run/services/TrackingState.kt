package com.brownx.runningapp.run.services

import com.google.android.gms.maps.model.LatLng

/**
 * @author Andrew Brown
 * created on 20/05/2024
 */
typealias Polyline = MutableList<LatLng>
typealias Polylines = MutableList<Polyline>

data class TrackingState(
    val isTracking: Boolean = false,
    val timeRunInMillis: Long = 0L,
    val pathPoints: Polylines = mutableListOf(),
    val timeStamp: Long = 0L,
    val myLocation: LatLng
)
