package com.brownx.runningapp.run.domain.usecase

import com.brownx.runningapp.run.services.Polyline
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

        val minutesPerKm = (elapsedTimeInMillis) / (distanceInMeters * 1000f)

        var millisPerKm = minutesPerKm.toLong()

        val minutes = TimeUnit.MILLISECONDS.toMinutes(millisPerKm)
        millisPerKm -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millisPerKm)

        return "${if(minutes < 10) "0" else ""}$minutes'" +
                "${if(seconds < 10) "0" else ""}$seconds\""
    }
}