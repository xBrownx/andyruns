package com.brownx.runningapp.run.domain.usecase

import com.brownx.runningapp.run.services.Polylines
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author Andrew Brown
 * created on 21/05/2024
 */
class CalculateAvgPaceUseCase @Inject constructor(
    val calculateTotalDistanceUseCase: CalculateTotalDistanceUseCase
) {

    operator fun invoke(pathPoints: Polylines, totalTimeInMillis: Long): String {


        val totalDistanceInMetres = calculateTotalDistanceUseCase(pathPoints)
        var millisPerKm = ((totalTimeInMillis / 1000f / 60) / (totalDistanceInMetres / 1000f)) * 60 * 1000

        val minutes = TimeUnit.MILLISECONDS.toMinutes(millisPerKm.toLong())
        millisPerKm -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millisPerKm.toLong())

        return "${if(minutes < 10) "0" else ""}$minutes'" +
                "${if(seconds < 10) "0" else ""}$seconds\""
    }
}