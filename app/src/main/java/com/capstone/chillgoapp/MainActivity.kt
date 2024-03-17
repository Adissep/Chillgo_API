package com.capstone.chillgoapp

//import com.capstone.chillgoapp.app.PostOfficeApp
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.capstone.chillgoapp.app.DashboardApp
import com.capstone.chillgoapp.services.PlacesWorker
import com.capstone.chillgoapp.ui.theme.ChillGoAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        setContent {
            ChillGoAppTheme {
                DashboardApp()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        startServiceViaWorker()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val name = "GEOFANCE"
            val descriptionText = "notification_chillgo"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(
                name,
                name,
                importance
            ).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun startServiceViaWorker() {
        Log.e("START", "WORKER")
        val workManager = WorkManager.getInstance(this)
        val request = PeriodicWorkRequest.Builder(
            PlacesWorker::class.java,
            15,
            TimeUnit.MINUTES
        ).build()

        workManager.enqueueUniquePeriodicWork(
            "PLACE_WORKER",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChillGoAppTheme {
        DashboardApp()
    }
}