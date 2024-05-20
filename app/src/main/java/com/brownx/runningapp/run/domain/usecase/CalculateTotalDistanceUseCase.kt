package com.brownx.runningapp.run.domain.usecase

import android.location.Location
import com.brownx.runningapp.run.services.Polylines
import javax.inject.Inject

/**
 * @author Andrew Brown
 * created on 20/05/2024
 */
class CalculateTotalDistanceUseCase @Inject constructor(
    val calculatePolylineDistanceUseCase: CalculatePolylineDistanceUseCase
) {

    operator fun invoke(pathPoints: Polylines): Int {
        var distanceInMeters = 0
        for(polyline in pathPoints) {
            distanceInMeters += calculatePolylineDistanceUseCase(polyline).toInt()
        }
        return distanceInMeters
    }

}