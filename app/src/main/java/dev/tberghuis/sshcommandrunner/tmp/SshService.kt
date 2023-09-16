package dev.tberghuis.sshcommandrunner.tmp

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dev.tberghuis.sshcommandrunner.FOREGROUND_SERVICE_NOTIFICATION_ID
import dev.tberghuis.sshcommandrunner.NOTIFICATION_CHANNEL
import dev.tberghuis.sshcommandrunner.util.logd

class SshService : Service() {
  override fun onBind(intent: Intent?): IBinder? {
    logd("SshService onbind")
    return null
  }

  override fun onCreate() {
    super.onCreate()
    logd("SshService oncreate")
  }

  override fun onDestroy() {
    logd("SshService ondestroy")
    super.onDestroy()
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    logd("SshService onStartCommand")


    startForeground()

    return START_NOT_STICKY
  }

  private fun startForeground() {
    startForeground(FOREGROUND_SERVICE_NOTIFICATION_ID, buildNotification())
  }

  private fun buildNotification(): Notification {
    val notification: Notification =
      NotificationCompat.Builder(this, NOTIFICATION_CHANNEL).setContentTitle("SSH command running")
        .build()
    return notification
  }
}
