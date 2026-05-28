package com.echo.worker

import com.echo.EchoApplication
import com.echo.features.friends.api.FriendsApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class FriendRequestWorker(
    context: Context,
    params: WorkerParameters
): CoroutineWorker(context, params){
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        return try {
            val appComponent = (applicationContext as EchoApplication).appComponent

            val retrofit = appComponent.retrofit()
            val api = retrofit.create(FriendsApi::class.java)

            val response = api.getIncomingRequests()
            if (response.isSuccessful) {
                val newRequests = response.body() ?: emptyList()
                val prefs = applicationContext.getSharedPreferences("notifications", Context.MODE_PRIVATE)
                val savedIds = prefs.getStringSet("pending_ids", emptySet()) ?: emptySet()
                val newIds = newRequests.map { it.firstFriendId.toString() }.toSet()
                val added = newIds - savedIds

                if (added.isNotEmpty()) {
                    showNotification(added.size)
                }
                prefs.edit().putStringSet("pending_ids", newIds).apply()
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(count: Int) {
        val channelId = "friend_requests"
        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, "Заявки в друзья", NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Новые заявки")
            .setContentText("У вас $count новых заявок в друзья")
            .setAutoCancel(true)
            .build()

        manager.notify(1, notification)
    }
}