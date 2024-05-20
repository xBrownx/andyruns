package com.brownx.runningapp.run.domain.usecase

import android.location.Location
import com.brownx.runningapp.run.services.Polyline

/**
 * @author Andrew Brown
 * created on 20/05/2024
 */
class CalculateIntervalDistanceUseCase {

    operator fun invoke(polyline: Polyline): Float {

        if(polyline.isEmpty() || polyline.size < 2) return 0f

        val pos1 = polyline[polyline.size - 2]
        val pos2 = polyline.last()

        val result = FloatArray(1)
        Location.distanceBetween(
            pos1.latitude,
            pos1.longitude,
            pos2.latitude,
            pos2.longitude,
            result
        )
        return result[0]
    }
}