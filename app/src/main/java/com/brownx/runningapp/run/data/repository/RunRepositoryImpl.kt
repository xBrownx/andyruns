package com.brownx.runningapp.run.data.repository

import com.brownx.runningapp.run.data.local.db.Run
import com.brownx.runningapp.run.data.local.db.RunDAO
import com.brownx.runningapp.run.domain.repository.RunRepository
import javax.inject.Inject

/**
 * @author Andrew Brown
 * created on 17/05/2024
 */
class RunRepositoryImpl @Inject constructor(
    private val runDao: RunDAO
) : RunRepository {

    override suspend fun insertRun(run: Run) = runDao.insertRun(run)

    override suspend fun deleteRun(run: Run) = runDao.deleteRun(run)

    override fun getAllRunsSortedByDate() = runDao.getAllRunsSortedByDate()

    override fun getAllRunsSortedByDistance() = runDao.getAllRunsSortedByDistance()

    override fun getAllRunsSortedByTimeInMillis() = runDao.getAllRunsSortedByTimeInMillis()

    override fun getAllRunsSortedByAvgSpeed() = runDao.getAllRunsSortedByAvgSpeed()

    override fun getAllRunsSortedByCaloriesBurned() = runDao.getAllRunsSortedByCaloriesBurned()

    override fun getTotalAvgSpeed() = runDao.getTotalAvgSpeed()

    override fun getTotalDistance() = runDao.getTotalDistance()

    override fun getTotalCaloriesBurned() = runDao.getTotalCaloriesBurned()

    override fun getTotalTimeInMillis() = runDao.getTotalTimeInMillis()

}