package com.brownx.runningapp.run.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * @author Andrew Brown
 * created on 17/05/2024
 */

@Database(entities = [Run::class], version = 1)
@TypeConverters(Converters::class)

abstract class RunningDatabase : RoomDatabase() {
    abstract fun getRunDao(): RunDAO
}