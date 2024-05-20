package com.brownx.runningapp.run.domain.usecase

/**
 * @author Andrew Brown
 * created on 20/05/2024
 */

data class MetricsUseCase (
    val calculateCurrentPaceUseCase: CalculateCurrentPaceUseCase,
    val calculateIntervalDistanceUseCase: CalculateIntervalDistanceUseCase,
    val calculatePolylineDistanceUseCase: CalculatePolylineDistanceUseCase,
    val calculateTotalDistanceUseCase: CalculateTotalDistanceUseCase,

)