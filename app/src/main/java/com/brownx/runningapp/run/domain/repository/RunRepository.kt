package com.brownx.runningapp.run.domain.repository

import androidx.lifecycle.LiveData
import com.brownx.runningapp.run.data.local.db.Run

/**
 * @author Andrew Brown
 * created on 17/05/2024
 */
interface RunRepository {

    suspend fun insertRun(run: Run)

    suspend fun deleteRun(run: Run)

    fun getAllRunsSortedByDate(): LiveData<List<Run>>

    fun getAllRunsSortedByDistance(): LiveData<List<Run>>

    fun getAllRunsSortedByTimeInMillis(): LiveData<List<Run>>

    fun getAllRunsSortedByAvgSpeed(): LiveData<List<Run>>

    fun getAllRunsSortedByCaloriesBurned(): LiveData<List<Run>>

    fun getTotalAvgSpeed(): LiveData<Float>

    fun getTotalDistance(): LiveData<Int>

    fun getTotalCaloriesBurned(): LiveData<Int>

    fun getTotalTimeInMillis(): LiveData<Long>

}