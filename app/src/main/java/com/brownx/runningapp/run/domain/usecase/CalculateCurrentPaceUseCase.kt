package com.brownx.runningapp.run.domain.usecase

import com.brownx.runningapp.run.services.Polyline
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author Andrew Brown
 * created on 20/05/2024
 */
class CalculateCurrentPaceUseCase @Inject constructor(
    val calculateIntervalDistanceUseCase: CalculateIntervalDistanceUseCase
) {
    operator fun invoke(polyline: Polyline?, lastTimeStamp: Long): String {

        if (polyline.isNullOrEmpty()) return "00'00\""

        val elapsedTimeInMillis = (System.currentTimeMillis() - lastTimeStamp)
        val distanceInMeters = calculateIntervalDistanceUseCase(polyline)

        var millisPerKm = ((elapsedTimeInMillis / 1000f / 60) / (distanceInMeters / 1000f)) * 60 * 1000

        val minutes = TimeUnit.MILLISECONDS.toMinutes(millisPerKm.toLong())
        millisPerKm -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millisPerKm.toLong())

        return "${if(minutes < 10) "0" else ""}$minutes'" +
                "${if(seconds < 10) "0" else ""}$seconds\""
    }
}