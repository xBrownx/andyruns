package com.brownx.runningapp.run.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.brownx.runningapp.run.data.local.db.RunningDatabase
import com.brownx.runningapp.run.domain.usecase.CalculateAvgPaceUseCase
import com.brownx.runningapp.run.domain.usecase.CalculateCurrentPaceUseCase
import com.brownx.runningapp.run.domain.usecase.CalculateIntervalDistanceUseCase
import com.brownx.runningapp.run.domain.usecase.CalculatePolylineDistanceUseCase
import com.brownx.runningapp.run.domain.usecase.CalculateTotalDistanceUseCase
import com.brownx.runningapp.run.domain.usecase.FormatStopWatchUseCase
import com.brownx.runningapp.run.domain.usecase.MetricsUseCase
import com.brownx.runningapp.util.Const.RUNNING_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Andrew Brown
 * created on 17/05/2024
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRunningDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        RunningDatabase::class.java,
        RUNNING_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(db: RunningDatabase) = db.getRunDao()

    @Singleton
    @Provides
    fun provideContext(
        application: Application
    ): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideStopWatchUseCase() : FormatStopWatchUseCase {
        return FormatStopWatchUseCase()
    }

    @Singleton
    @Provides
    fun provideCalculatePolylineDistanceUseCase() : CalculatePolylineDistanceUseCase {
        return CalculatePolylineDistanceUseCase()
    }

    @Singleton
    @Provides
    fun provideCalculateTotalDistanceUseCase() : CalculateTotalDistanceUseCase {
        return CalculateTotalDistanceUseCase(
            calculatePolylineDistanceUseCase = CalculatePolylineDistanceUseCase()
        )
    }

    @Singleton
    @Provides
    fun provideCalculateCurrentPaceUseCase() : CalculateCurrentPaceUseCase {
        return CalculateCurrentPaceUseCase(
            calculateIntervalDistanceUseCase = CalculateIntervalDistanceUseCase()
        )
    }

    @Singleton
    @Provides
    fun provideMetricsUseCase() : MetricsUseCase {
        return MetricsUseCase(
            CalculateCurrentPaceUseCase(calculateIntervalDistanceUseCase = CalculateIntervalDistanceUseCase()),
            CalculateIntervalDistanceUseCase(),
            CalculatePolylineDistanceUseCase(),
            CalculateTotalDistanceUseCase(calculatePolylineDistanceUseCase = CalculatePolylineDistanceUseCase()),
            CalculateAvgPaceUseCase(
                calculateTotalDistanceUseCase = CalculateTotalDistanceUseCase(
                    calculatePolylineDistanceUseCase = CalculatePolylineDistanceUseCase()
                )
            )
        )
    }



}