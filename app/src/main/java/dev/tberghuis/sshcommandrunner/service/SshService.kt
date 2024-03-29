package dev.tberghuis.sshcommandrunner.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dev.tberghuis.sshcommandrunner.FOREGROUND_SERVICE_NOTIFICATION_ID
import dev.tberghuis.sshcommandrunner.MyApplication
import dev.tberghuis.sshcommandrunner.NOTIFICATION_CHANNEL
import dev.tberghuis.sshcommandrunner.R
import dev.tberghuis.sshcommandrunner.REQUEST_CODE_VIEW_SSH_COMMAND
import dev.tberghuis.sshcommandrunner.tmp2.Command
import dev.tberghuis.sshcommandrunner.util.logd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SshService : Service() {
  private val job = SupervisorJob()
  private val scope = CoroutineScope(Dispatchers.Default + job)

  private var sshController: SshController? = null

  private val binder = LocalBinder()

  inner class LocalBinder : Binder() {
    fun getService(): SshService = this@SshService
  }

  fun runCommand(
    id: Int,
    callback: (Command, SshSessionState) -> Unit
  ) {
    // continue existing session
    if (sshController != null && id == sshController!!.command.id) {
      callback(sshController!!.command, sshController!!.sshSessionState)
      return
    }

    sshController?.hangup()

    startForeground(id)

    scope.launch(IO) {
      val commandDao = (application as MyApplication).database.commandDao()
      val command = commandDao.loadCommandById(id)
      sshController = SshController(scope, command)
      callback(command, sshController!!.sshSessionState)
      sshController!!.runCommand()
    }
  }

  fun hangup() {
    sshController?.hangup()
  }

  override fun onBind(intent: Intent?): IBinder? {
    logd("SshService onbind")
    return binder
  }

  override fun onCreate() {
    super.onCreate()
    logd("SshService oncreate")
  }

  override fun onDestroy() {
    logd("SshService ondestroy")
    sshController?.hangup()
    job.cancel()
    super.onDestroy()
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    logd("SshService onStartCommand")
//    startForeground()
    return START_NOT_STICKY
  }

  private fun startForeground(commandId: Int) {
    startForeground(FOREGROUND_SERVICE_NOTIFICATION_ID, buildNotification(commandId))
  }

  private fun buildNotification(commandId: Int): Notification {
    val intent = Intent(
      Intent.ACTION_VIEW,
      Uri.parse("deeplink://ssh.command.runner/command/$commandId")
    )
    val pendingIntent = PendingIntent.getActivity(
      this,
      REQUEST_CODE_VIEW_SSH_COMMAND,
      intent,
      PendingIntent.FLAG_IMMUTABLE
    )

    val notification: Notification =
      NotificationCompat.Builder(this, NOTIFICATION_CHANNEL).setContentTitle("SSH command running")
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentIntent(pendingIntent)
        .setOngoing(true)
        .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
        .build()
    return notification
  }
}
