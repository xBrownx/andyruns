package com.brownx.runningapp.run.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.brownx.runningapp.R
import com.brownx.runningapp.core.presenter.MainActivity
import com.brownx.runningapp.util.Const.ACTION_PAUSE_SERVICE
import com.brownx.runningapp.util.Const.ACTION_SHOW_TRACKING_FRAGMENT
import com.brownx.runningapp.util.Const.ACTION_START_OR_RESUME_SERVICE
import com.brownx.runningapp.util.Const.ACTION_STOP_SERVICE
import com.brownx.runningapp.util.Const.FASTEST_LOCATION_INTERVAL
import com.brownx.runningapp.util.Const.LOCATION_UPDATE_INTERVAL
import com.brownx.runningapp.util.Const.NOTIFICATION_CHANNEL_ID
import com.brownx.runningapp.util.Const.NOTIFICATION_CHANNEL_NAME
import com.brownx.runningapp.util.Const.NOTIFICATION_ID
import com.brownx.runningapp.run.util.TrackingUtil
import com.brownx.runningapp.util.Const.SYDNEY
import com.brownx.runningapp.util.Const.TIMER_UPDATE_INTERVAL
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * @author Andrew Brown
 * created on 18/05/2024
 */


class TrackingService : LifecycleService() {

    private var isFirstRun = true

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val timeRunInSeconds = MutableLiveData<Long>()

    companion object {
        val trackingState = MutableStateFlow(TrackingState(myLocation = SYDNEY))
    }

    private fun postInitialValues() {
        timeRunInSeconds.postValue(0L)
    }

    override fun onCreate() {
        super.onCreate()
        postInitialValues()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        CoroutineScope(Dispatchers.Main).launch {
            trackingState.asStateFlow().collect {
                updateLocationTracking(it.isTracking)
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getMyLocation() {
        if (TrackingUtil.hasLocationPermissions(this)) {
            fusedLocationProviderClient.getCurrentLocation(
                PRIORITY_HIGH_ACCURACY,
                object : CancellationToken() {
                    override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                        CancellationTokenSource().token

                    override fun isCancellationRequested() = false
                }).addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val lat = location.latitude
                    val lng = location.longitude
                    trackingState.update {
                        it.copy(myLocation = LatLng(lat, lng))
                    }
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    if (isFirstRun) {
                        startForegroundService()
                        isFirstRun = false
                        Timber.d("Start service")
                    } else {
                        startTimer()
                        Timber.d("Resumed service")
                    }
                }

                ACTION_PAUSE_SERVICE -> {
                    pauseService()
                    Timber.d("Paused service")
                }

                ACTION_STOP_SERVICE -> {
                    Timber.d("Stopped service")
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private var isTimerEnabled = false
    private var lapTime = 0L
    private var timeRun = 0L
    private var timeStarted = 0L
    private var lastSecondTimestamp = 0L

    private fun startTimer() {
        addEmptyPolyline()
        trackingState.update {
            it.copy(isTracking = true)
        }
        timeStarted = System.currentTimeMillis()
        isTimerEnabled = true
        CoroutineScope(Dispatchers.Main).launch {
            while (trackingState.value.isTracking) {

                lapTime = System.currentTimeMillis() - timeStarted

                trackingState.update {
                    it.copy(
                        timeRunInMillis = timeRun + lapTime,
                    )
                }

                if (trackingState.value.timeRunInMillis >= lastSecondTimestamp + 1000L) {
                    timeRunInSeconds.postValue(timeRunInSeconds.value!! + 1)
                    lastSecondTimestamp += 1000L
                }
                delay(TIMER_UPDATE_INTERVAL)
            }
            timeRun += lapTime
        }
    }

    private fun pauseService() {
        trackingState.update {
            it.copy(isTracking = false)
        }
//        isTracking.update { false }
        isTimerEnabled = false
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationTracking(isTracking: Boolean) {
        if (isTracking) {
            if (TrackingUtil.hasLocationPermissions(this)) {
                val request = LocationRequest().apply {
                    interval = LOCATION_UPDATE_INTERVAL
                    fastestInterval = FASTEST_LOCATION_INTERVAL
                    priority - Priority.PRIORITY_HIGH_ACCURACY
                }
                fusedLocationProviderClient.requestLocationUpdates(
                    request,
                    locationCallback,
                    Looper.getMainLooper()
                )
            }
        } else {
            getMyLocation()
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            if (trackingState.value.isTracking) {
                result.locations.let { locations ->
                    for (location in locations) {
                        addPathPoint(location)
                        trackingState.update {
                            it.copy(
                                myLocation = LatLng(location.latitude, location.longitude),
                                timeStamp = System.currentTimeMillis()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun addPathPoint(location: Location?) {
        location?.let {
            val pos = LatLng(location.latitude, location.longitude)
            trackingState.update {
                it.copy(pathPoints = it.pathPoints.also { it2 ->
                    it2.last().add(pos)
                })
            }
        }
    }

    private fun addEmptyPolyline() = trackingState.update {
        it.copy(
            pathPoints = it.pathPoints.also { it2 ->
                it2.add(mutableListOf())
            }
        )
    }

    private fun startForegroundService() {
        startTimer()
        trackingState.update {
            it.copy(isTracking = true)
        }
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Andy Runs")
            .setContentText("00:00:00")
            .setContentIntent(getMainActivityPendingIntent())

        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun getMainActivityPendingIntent() = PendingIntent.getActivity(
        this,
        0,
        Intent(this, MainActivity::class.java).also {
            it.action = ACTION_SHOW_TRACKING_FRAGMENT
        },
        FLAG_IMMUTABLE + FLAG_UPDATE_CURRENT

    )

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }


}