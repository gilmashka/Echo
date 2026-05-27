package com.echo

import android.app.Application
import androidx.work.*
import com.echo.core.network.di.AppComponent
import com.echo.core.network.di.DaggerAppComponent
import com.echo.worker.FriendRequestWorker
import java.util.concurrent.TimeUnit

class EchoApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<FriendRequestWorker>(15, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "friend_requests",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}