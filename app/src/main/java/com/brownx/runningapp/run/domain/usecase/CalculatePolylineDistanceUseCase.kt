package com.brownx.runningapp.run.domain.usecase

import android.location.Location
import com.brownx.runningapp.run.services.Polyline

/**
 * @author Andrew Brown
 * created on 20/05/2024
 */
class CalculatePolylineDistanceUseCase {

    operator fun invoke(polyline: Polyline): Float {
        var distance = 0f
        for (i in 0..polyline.size - 2) {
            val pos1 = polyline[i]
            val pos2 = polyline[i + 1]

            val result = FloatArray(1)
            Location.distanceBetween(
                pos1.latitude,
                pos1.longitude,
                pos2.latitude,
                pos2.longitude,
                result
            )
            distance += result[0]
        }
        return distance
    }
}