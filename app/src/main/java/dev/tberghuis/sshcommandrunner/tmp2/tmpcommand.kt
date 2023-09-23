package dev.tberghuis.sshcommandrunner.tmp2

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import dev.tberghuis.sshcommandrunner.ui.CommandScreenState
import kotlinx.coroutines.flow.Flow

@Entity
data class Command(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  val title: String,
  val host: String,
  val user: String,
  val password: String,
  // should probably rename to commandText
  val command: String,

  @ColumnInfo(defaultValue = "false") val isLocalPortForward: Boolean = false,
  @ColumnInfo(defaultValue = "") val localHost: String = "",
  @ColumnInfo(defaultValue = "") val localPort: String = "",
  @ColumnInfo(defaultValue = "") val remoteHost: String = "",
  @ColumnInfo(defaultValue = "") val remotePort: String = ""
) {

  companion object {
    fun create(id: Int = 0, css: CommandScreenState): Command {
      return Command(
        id = id,
        title = css.title,
        host = css.host,
        user = css.user,
        password = css.password,
        command = css.command,
      )
    }
  }
}

@Dao
interface CommandDao {
  @Query("SELECT * FROM command")
  fun getAll(): Flow<List<Command>>

  @Query("SELECT * FROM command where id = :id")
  suspend fun loadCommandById(id: Int): Command

  @Update
  fun update(command: Command)

  @Insert
  fun insertAll(vararg commands: Command)

  @Delete
  fun delete(command: Command)

  @Query("delete FROM command where id = :id")
  fun deleteById(id: Int)
}