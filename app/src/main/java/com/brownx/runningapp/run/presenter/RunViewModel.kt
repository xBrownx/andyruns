package com.brownx.runningapp.run.presenter

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.brownx.runningapp.run.domain.repository.RunRepository
import com.brownx.runningapp.run.domain.usecase.CalculateCurrentPaceUseCase
import com.brownx.runningapp.run.domain.usecase.CalculateTotalDistanceUseCase
import com.brownx.runningapp.run.domain.usecase.FormatStopWatchUseCase
import com.brownx.runningapp.run.domain.usecase.MetricsUseCase
import com.brownx.runningapp.run.services.TrackingService
import com.brownx.runningapp.util.Const.ACTION_PAUSE_SERVICE
import com.brownx.runningapp.util.Const.ACTION_START_OR_RESUME_SERVICE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Andrew Brown
 * created on 17/05/2024
 */
@HiltViewModel
class RunViewModel @Inject constructor(
    private val application: Application,
    private val formatStopWatchUseCase: FormatStopWatchUseCase,
    private val metricsUseCase: MetricsUseCase,
) : ViewModel() {

    private val _runState = MutableStateFlow(RunState())
    val runState = _runState.asStateFlow()

    init {
        viewModelScope.launch {
            TrackingService.trackingState.asStateFlow().collect { state ->
                _runState.update {
                    it.copy(
                        pathPoints = state.pathPoints,
                        totalTimeRunInMillis = formatStopWatchUseCase(state.timeRunInMillis, true),
                        isTracking = state.isTracking,
                        myLocation = state.myLocation,
                        lastTimeStamp = state.timeStamp,
                        currentPaceInMinutesPerKm =
                        if (
                            state.pathPoints.isNotEmpty() &&
                            state.pathPoints.last().isNotEmpty()
                            && it.lastTimeStamp != state.timeStamp
                        )
                            metricsUseCase.calculateCurrentPaceUseCase(
                                polyline = state.pathPoints.last(),
                                lastTimeStamp = it.lastTimeStamp
                            )
                        else
                            it.currentPaceInMinutesPerKm,
                        avgPaceInMinutesPerKm =
                        if (
                            state.pathPoints.isNotEmpty()
                            && it.lastTimeStamp != state.timeStamp
                        )
                            metricsUseCase.calculateAvgPaceUseCase(
                                pathPoints = state.pathPoints,
                                totalTimeInMillis = state.timeRunInMillis
                            )
                        else
                            it.avgPaceInMinutesPerKm,
                        intervalTimeRemainingInMillis = getIntervalRemainingTime()

                    )
                }
            }
        }
    }

    private fun getIntervalRemainingTime(): Long {
        val
    }

    fun onEvent(uiEvent: RunUiEvents) {
        when (uiEvent) {

            is RunUiEvents.OnToggleLockScreen -> {
                _runState.update {
                    it.copy(isScreenLocked = !it.isScreenLocked)
                }
            }

            is RunUiEvents.OnToggleRun -> {
                toggleRun()
            }
        }
    }

    private fun sendCommandToService(action: String) =
        Intent(application, TrackingService::class.java).also {
            it.action = action
            application.startService(it)
        }

    private fun toggleRun() {
        if (runState.value.isTracking) {
            sendCommandToService(ACTION_PAUSE_SERVICE)
        } else {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }
    }

    fun updateDistance() {
        _runState.update {
            it.copy(
                totalDistanceInMetres =
                metricsUseCase.calculateTotalDistanceUseCase(runState.value.pathPoints)
            )
        }
    }
}