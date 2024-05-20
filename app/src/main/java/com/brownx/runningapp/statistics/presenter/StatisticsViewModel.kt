package com.brownx.runningapp.statistics.presenter

import androidx.lifecycle.ViewModel
import com.brownx.runningapp.run.domain.repository.RunRepository
import javax.inject.Inject

/**
 * @author Andrew Brown
 * created on 17/05/2024
 */
class StatisticsViewModel @Inject constructor(
    val runRepository: RunRepository
): ViewModel() {


}