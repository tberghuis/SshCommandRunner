package dev.tberghuis.sshcommandrunner.data

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.tberghuis.sshcommandrunner.tmp2.Command
import dev.tberghuis.sshcommandrunner.tmp2.CommandDao

@Database(
  entities = [
    Command::class,
  ],
  version = 1,
  exportSchema = false
)
abstract class MyDatabase : RoomDatabase() {
  abstract fun commandDao(): CommandDao
}