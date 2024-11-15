package com.wcd.farm.fcm

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.wcd.farm.R
import com.wcd.farm.data.remote.UserApi
import com.wcd.farm.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MyFirebaseMessagingService: FirebaseMessagingService() {

    @Inject
    lateinit var userApi: UserApi

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if(!isAppInForeground()) {
            if (remoteMessage.data.isNotEmpty()) {
                Log.e(TAG, "Message data payload: ${remoteMessage.data}")
                sendNotification(
                    remoteMessage.data["title"].toString(),
                    remoteMessage.data["body"].toString()
                )
            } else {
                remoteMessage.notification?.let {
                    sendNotification(
                        remoteMessage.notification!!.title.toString(),
                        remoteMessage.notification!!.body.toString()
                    )
                }
            }
        } else {
            Log.e("TEST", remoteMessage.notification!!.imageUrl.toString())
        }
        //Log.e("TEST", remoteMessage.data["image"])

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sendRegistrationToServer(token)
    }

    private fun scheduleJob() {
        val work = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .build()
        WorkManager.getInstance(this)
            .beginWith(work)
            .enqueue()
    }

    private fun handleNow() {
        Log.e(TAG, "Short lived task is done.")
    }

    private fun sendRegistrationToServer(token: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            if (token != null) {
                val response = userApi.setFcmToken(token)

                if(response.isSuccessful) {
                    Log.e("TEST", response.body().toString())
                } else {
                    Log.e("TEST", response.errorBody()!!.string())
                }
            }
        }

    }

    private fun sendNotification(title: String, body: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = "fcm_default_channel"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.camera_btn)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "Channel human readable title",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        notificationManager.notify(0, notificationBuilder.build())
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }

    internal class MyWorker(appContext: Context, workerParams: WorkerParameters) :
        Worker(appContext, workerParams) {
        override fun doWork(): Result {
            return Result.success()
        }
    }

    private fun isAppInForeground(): Boolean {
        // 앱의 포그라운드 상태 확인 (현재 액티비티가 실행 중인지 확인)
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val appProcesses = activityManager.runningAppProcesses

        for (appProcess in appProcesses) {
            if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                // 포그라운드 상태일 때
                return true
            }
        }
        return false
    }
}