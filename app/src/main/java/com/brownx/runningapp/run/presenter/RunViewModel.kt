package com.brownx.runningapp.run.presenter

import androidx.lifecycle.ViewModel
import com.brownx.runningapp.run.domain.repository.RunRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Andrew Brown
 * created on 17/05/2024
 */
@HiltViewModel
class RunViewModel @Inject constructor(
    val runRepository: RunRepository
): ViewModel() {


}