package dev.tberghuis.sshcommandrunner.tmp

import android.app.Service
import android.content.Intent
import android.os.IBinder
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
    return START_NOT_STICKY
  }
}
