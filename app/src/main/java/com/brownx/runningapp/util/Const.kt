package com.brownx.runningapp.util

import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.LatLng

/**
 * @author Andrew Brown
 * created on 17/05/2024
 */
object Const {

    const val RUNNING_DATABASE_NAME = "running_db"

    const val REQUEST_CODE_LOCATION_PERMISSION = 0

    const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
    const val ACTION_PAUSE_SERVICE = "ACTION_PAUSE_SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
    const val ACTION_SHOW_TRACKING_FRAGMENT = "ACTION_SHOW_TRACKING_FRAGMENT"

    const val TIMER_UPDATE_INTERVAL = 50L

    val POLYLINE_COLOR = Color.Red
    const val POLYLINE_WIDTH = 8f
    const val MAP_ZOOM = 15f

    const val LOCATION_UPDATE_INTERVAL = 2000L
    const val FASTEST_LOCATION_INTERVAL = 1000L

    const val NOTIFICATION_CHANNEL_ID = "tracking_channel"
    const val NOTIFICATION_CHANNEL_NAME = "Tracking"
    const val NOTIFICATION_ID = 1

    val SYDNEY = LatLng(-33.8688, 151.2093)

}