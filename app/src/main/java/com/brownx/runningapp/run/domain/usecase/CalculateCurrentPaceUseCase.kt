package com.brownx.runningapp.run.domain.usecase

import com.brownx.runningapp.run.services.Polyline
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.round

/**
 * @author Andrew Brown
 * created on 20/05/2024
 */
class CalculateCurrentPaceUseCase @Inject constructor(
    val calculateIntervalDistanceUseCase: CalculateIntervalDistanceUseCase
) {
    operator fun invoke(polyline: Polyline?, lastTimeStamp: Long): Float {
        if(polyline.isNullOrEmpty()) return 0f

        val elapsedTimeInSeconds = (System.currentTimeMillis() - lastTimeStamp) / 1000f
        val distanceInMeters = calculateIntervalDistanceUseCase(polyline)
        val paceInMinutesPerKM =  (elapsedTimeInSeconds / 60) / (distanceInMeters / 1000f)

        val minutes = TimeUnit.MILLISECONDS.toMinutes(paceInMinutesPerKM.toLong())


//        val paceInMinutesPerKM = round((distanceInMeters / 1000f) / (2000f / 1000f / 60 / 60) * 10) / 10f
        return paceInMinutesPerKM
    }
}