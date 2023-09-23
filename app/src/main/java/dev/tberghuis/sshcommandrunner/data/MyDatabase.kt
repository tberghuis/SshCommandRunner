package dev.tberghuis.sshcommandrunner.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import dev.tberghuis.sshcommandrunner.tmp2.Command
import dev.tberghuis.sshcommandrunner.tmp2.CommandDao

@Database(
  entities = [
    Command::class,
  ],
  version = 2,
  exportSchema = true,
  autoMigrations = [
    AutoMigration(from = 1, to = 2)
  ]
)
abstract class MyDatabase : RoomDatabase() {
  abstract fun commandDao(): CommandDao
}