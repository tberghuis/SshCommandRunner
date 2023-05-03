package dev.tberghuis.sshcommandrunner

import android.app.Application
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


  // todo test if even needed
  //  https://stackoverflow.com/questions/26653399/android-sshj-exception-upon-connect-keyfactory-ecdsa-implementation-not-fou
  companion object {
    init {
      Security.removeProvider("BC") //first remove default os provider
      Security.insertProviderAt(BouncyCastleProvider(), 1) //add new provider
    }
  }
}