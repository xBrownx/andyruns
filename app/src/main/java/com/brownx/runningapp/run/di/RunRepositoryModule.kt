package com.brownx.runningapp.run.di

import com.brownx.runningapp.run.data.repository.RunRepositoryImpl
import com.brownx.runningapp.run.domain.repository.RunRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Andrew Brown
 * created on 17/05/2024
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class RunRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRunRepository(
        runRepositoryImpl: RunRepositoryImpl
    ) : RunRepository
}