package dev.tberghuis.sshcommandrunner.data

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
data class XCommand(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  val title: String,
  val host: String,
  val user: String,
  val password: String,
  // should probably rename to commandText
  val command: String,
) {

  companion object {
    fun create(id: Int = 0, css: CommandScreenState): XCommand {
      return XCommand(
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
interface XCommandDao {
  @Query("SELECT * FROM command")
  fun getAll(): Flow<List<XCommand>>

  @Query("SELECT * FROM command where id = :id")
  suspend fun loadCommandById(id: Int): XCommand

  @Update
  fun update(command: XCommand)

  @Insert
  fun insertAll(vararg commands: XCommand)

  @Delete
  fun delete(command: XCommand)

  @Query("delete FROM command where id = :id")
  fun deleteById(id: Int)
}