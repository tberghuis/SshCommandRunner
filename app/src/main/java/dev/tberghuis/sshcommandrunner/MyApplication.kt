package dev.tberghuis.sshcommandrunner

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.room.Room
import dev.tberghuis.sshcommandrunner.data.MyDatabase
import java.security.Security
import org.bouncycastle.jce.provider.BouncyCastleProvider

class MyApplication : Application() {
  // doitwrong by lazy
  // should probably use hilt

  val database by lazy {
    Room.databaseBuilder(this, MyDatabase::class.java, "data.db")
      .fallbackToDestructiveMigration()
      .build()
  }

  override fun onCreate() {
    super.onCreate()
    createNotificationChannel()
  }


  private fun createNotificationChannel() {
    val notificationChannel = NotificationChannel(
      NOTIFICATION_CHANNEL, NOTIFICATION_CHANNEL_DISPLAY, NotificationManager.IMPORTANCE_DEFAULT
    )
    // channel description???
    val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    manager.createNotificationChannel(notificationChannel)
  }


  // todo test if even needed
  //  https://stackoverflow.com/questions/26653399/android-sshj-exception-upon-connect-keyfactory-ecdsa-implementation-not-fou
  companion object {
    init {
      Security.removeProvider("BC") //first remove default os provider
      Security.insertProviderAt(BouncyCastleProvider(), 1) //add new provider
    }
  }
}