package com.brownx.runningapp.core.presenter

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.brownx.runningapp.core.presenter.components.AppBottomBar
import com.brownx.runningapp.log.presenter.LogScreen
import com.brownx.runningapp.profile.presenter.ProfileScreen
import com.brownx.runningapp.run.presenter.RunScreen
import com.brownx.runningapp.run.services.TrackingService
import com.brownx.runningapp.settings.presenter.SettingsScreen
import com.brownx.runningapp.statistics.presenter.StatisticsScreen
import com.brownx.runningapp.ui.theme.RunningAppTheme
import com.brownx.runningapp.util.Const
import com.brownx.runningapp.util.Const.ACTION_START_OR_RESUME_SERVICE
import com.brownx.runningapp.util.Const.REQUEST_CODE_LOCATION_PERMISSION
import com.brownx.runningapp.util.Screen
import com.brownx.runningapp.run.util.TrackingUtil
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class MainActivity : ComponentActivity(), EasyPermissions.PermissionCallbacks {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
//        navigateToTrackingFragment(intent)
        setContent {
            RunningAppTheme {
                val navController = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.Run.route,
                            modifier = Modifier.weight(1f)
                        ) {
                            composable(Screen.Log.route) { LogScreen(modifier = Modifier) }
                            composable(Screen.Stats.route) { StatisticsScreen(modifier = Modifier) }
                            composable(Screen.Run.route) { RunScreen(modifier = Modifier) }
                            composable(Screen.Profile.route) { ProfileScreen(modifier = Modifier) }
                            composable(Screen.Settings.route) { SettingsScreen(modifier = Modifier) }
                        }
                        AppBottomBar(navController = navController)
                    }
                }
            }
        }
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        navigateToTrackingFragment(intent)
    }

    private fun requestPermissions() {
        if (TrackingUtil.hasLocationPermissions(this)) {
            return
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions to use this app.",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions to use this app.",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun sendCommandToService(action: String) =
        Intent(this, TrackingService::class.java).also {
            it.action = action
            this.startService(it)
        }


    private fun navigateToTrackingFragment(intent: Intent?) {
        if (intent?.action == Const.ACTION_SHOW_TRACKING_FRAGMENT) {
            //
        }
    }
}

