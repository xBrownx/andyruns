package com.brownx.runningapp.util

/**
 * @author Andrew Brown
 * created on 17/05/2024
 */
sealed class Screen(val route: String) {
    object Log : Screen("log")
    object Stats : Screen("stats")
    object Run : Screen("run")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
}