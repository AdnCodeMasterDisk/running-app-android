package com.runningapp.app.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.model.LatLng
import com.runningapp.app.R
import com.runningapp.app.ui.MainActivity
import com.runningapp.app.ui.utils.Constants.ACTION_PAUSE_SERVICE
import com.runningapp.app.ui.utils.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import com.runningapp.app.ui.utils.Constants.ACTION_START_OR_RESUME_SERVICE
import com.runningapp.app.ui.utils.Constants.ACTION_STOP_SERVICE
import com.runningapp.app.ui.utils.Constants.FASTEST_LOCATION_INTERVAL
import com.runningapp.app.ui.utils.Constants.LOCATION_UPDATE_INTERVAL
import com.runningapp.app.ui.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.runningapp.app.ui.utils.Constants.NOTIFICATION_CHANNEL_NAME
import com.runningapp.app.ui.utils.Constants.NOTIFICATION_ID
import com.runningapp.app.ui.utils.Constants.TIMER_UPDATE_INTERVAL
import com.runningapp.app.ui.utils.TrackingUtility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

typealias Leg = MutableList<LatLng>
typealias Route = MutableList<Leg>

class TrackingService : LifecycleService() {

    private var isStart = true
    private var serviceKilled = false

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val timeRunInSeconds = MutableLiveData<Long>()

    private lateinit var baseNotificationBuilder: NotificationCompat.Builder
    private lateinit var curNotificationBuilder: NotificationCompat.Builder

    companion object {
        val timeRunInMilliseconds = MutableLiveData<Long>()
        val isTracking = MutableLiveData<Boolean>()
        val routes = MutableLiveData<Route>()
    }

    private fun postInitialValues() {
        isTracking.postValue(false)
        routes.postValue(mutableListOf())
        timeRunInSeconds.postValue(0L)
        timeRunInMilliseconds.postValue(0L)
    }

    override fun onCreate() {
        super.onCreate()
        baseNotificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_directions_run_black_24dp)
            .setContentTitle("Running App")
            .setContentText("00:00:00")
            .setContentIntent(getMainActivityPendingIntent())

        curNotificationBuilder = baseNotificationBuilder
        postInitialValues()
        fusedLocationProviderClient = FusedLocationProviderClient(this)

        isTracking.observe(this) {
            updateLocationTracking(it)
            updateNotification(it)
        }
    }

    private fun killService() {
        serviceKilled = true
        isStart = true
        pauseService()
        postInitialValues()
        stopForeground(true)
        stopSelf()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    if (isStart) {
                        startForegroundService()
                        isStart = false
                    } else {
                        startTimer()
                    }
                }
                ACTION_PAUSE_SERVICE -> {
                    pauseService()
                }
                ACTION_STOP_SERVICE -> {
                    killService()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private var isTimerEnabled = false
    private var legTime = 0L
    private var timeRun = 0L
    private var timeStarted = 0L
    private var lastSecondTimestamp = 0L

    private fun startTimer() {
        startNewLeg()
        isTracking.postValue(true)
        timeStarted = System.currentTimeMillis()
        isTimerEnabled = true
        CoroutineScope(Dispatchers.Main).launch {
            while (isTracking.value!!) {
                legTime = System.currentTimeMillis() - timeStarted
                timeRunInMilliseconds.postValue(timeRun + legTime)
                if (timeRunInMilliseconds.value!! >= lastSecondTimestamp + 1000L) {
                    timeRunInSeconds.postValue(timeRunInSeconds.value!! + 1)
                    lastSecondTimestamp += 1000L
                }
                delay(TIMER_UPDATE_INTERVAL)
            }
            timeRun += legTime
        }
    }

    private fun getMainActivityPendingIntent() = PendingIntent.getActivity(
        this,
        0,
        Intent(this, MainActivity::class.java).also {
            it.action = ACTION_SHOW_TRACKING_FRAGMENT
        },
        FLAG_UPDATE_CURRENT
    )

    private fun pauseService() {
        isTracking.postValue(false)
        isTimerEnabled = false
    }

    private fun updateNotification(isTracking: Boolean) {
        val notificationActionText = if (isTracking) "Pause" else "Resume"
        val pendingIntent = if (isTracking) {
            val pauseIntent = Intent(this, TrackingService::class.java).apply {
                action = ACTION_PAUSE_SERVICE
            }
            PendingIntent.getService(this, 1, pauseIntent, FLAG_UPDATE_CURRENT)
        } else {
            val resumeIntent = Intent(this, TrackingService::class.java).apply {
                action = ACTION_START_OR_RESUME_SERVICE
            }
            PendingIntent.getService(this, 2, resumeIntent, FLAG_UPDATE_CURRENT)
        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        curNotificationBuilder.javaClass.getDeclaredField("mActions").apply {
            isAccessible = true
            set(curNotificationBuilder, ArrayList<NotificationCompat.Action>())
        }
        if (!serviceKilled) {
            curNotificationBuilder = baseNotificationBuilder
                // TODO: change to pause icon
                .addAction(
                    R.drawable.ic_directions_run_black_24dp,
                    notificationActionText,
                    pendingIntent
                )
            notificationManager.notify(NOTIFICATION_ID, curNotificationBuilder.build())
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationTracking(isTracking: Boolean) {
        if (isTracking) {
            // TODO: Change to not deprecated (fusedLocationProvider request)
            // if (TrackingUtility.hasLocationPermissions(this)) {
            val request = LocationRequest().apply {
                interval = LOCATION_UPDATE_INTERVAL
                fastestInterval = FASTEST_LOCATION_INTERVAL
                priority = PRIORITY_HIGH_ACCURACY
            }
            fusedLocationProviderClient.requestLocationUpdates(
                request,
                locationCallback,
                Looper.getMainLooper()
            )
//            }
        } else {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            super.onLocationResult(result)
            if (isTracking.value!!) {
                result?.locations?.let { locations ->
                    for (location in locations) {
                        addPathPoint(location)
                        println("NEW LOCATION: ${location.latitude}, ${location.longitude}")
                    }
                }
            }
        }
    }

    private fun addPathPoint(location: Location?) {
        location?.let {
            val pos = LatLng(location.latitude, location.longitude)
            routes.value?.apply {
                last().add(pos)
                routes.postValue(this)
            }
        }
    }

    private fun startForegroundService() {
        startTimer()
        isTracking.postValue(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        startForeground(NOTIFICATION_ID, baseNotificationBuilder.build())

        timeRunInSeconds.observe(this) {
            if (!serviceKilled) {
                val notification = curNotificationBuilder
                    .setContentText(TrackingUtility.getFormattedStopWatchTime(it * 1000L))
                notificationManager.notify(NOTIFICATION_ID, notification.build())
            }
        }
    }

    private fun startNewLeg() = routes.value?.apply {
        add(mutableListOf())
        routes.postValue(this)
    } ?: routes.postValue(mutableListOf(mutableListOf()))

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }
}


