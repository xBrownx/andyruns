package com.brownx.runningapp.run.presenter

/**
 * @author Andrew Brown
 * created on 20/05/2024
 */
sealed class RunUiEvents {

    data object OnToggleLockScreen : RunUiEvents()

    data object OnToggleRun : RunUiEvents()
}