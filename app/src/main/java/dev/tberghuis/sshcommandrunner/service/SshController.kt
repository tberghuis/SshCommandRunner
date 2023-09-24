package dev.tberghuis.sshcommandrunner.service

import dev.tberghuis.sshcommandrunner.tmp2.Command
import dev.tberghuis.sshcommandrunner.util.logd
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import net.schmizz.sshj.SSHClient
import net.schmizz.sshj.common.IOUtils
import net.schmizz.sshj.connection.channel.direct.Parameters
import net.schmizz.sshj.connection.channel.direct.Session
import net.schmizz.sshj.connection.channel.direct.Signal
import net.schmizz.sshj.transport.verification.PromiscuousVerifier

// step 1 hardcode
class SshController(
  private val scope: CoroutineScope,
  val command: Command
) {
  val sshSessionState = SshSessionState()

  private val ssh = SSHClient()
  private var session: Session? = null
  private var cmd: Session.Command? = null
  private var cmdJob: Job? = null

  fun runCommand() {
    try {
      ssh.addHostKeyVerifier(PromiscuousVerifier())
      ssh.connect(command.host)
      ssh.authPassword(command.user, command.password)
      session = ssh.startSession()

      if (!command.command.isNullOrBlank()) {
        cmd = session!!.exec(command.command)
        val sshFlow = cmd!!.inputStream.bufferedReader().lineSequence().asFlow()
        cmdJob = scope.launch(IO) {
          sshFlow.cancellable().collect {
            logd("line: $it")
            sshSessionState.commandOutput.value += it
          }
        }
      }
      if (command.isLocalPortForward) {
        val params =
          Parameters(
            command.localHost,
            command.localPort.toInt(),
            command.remoteHost,
            command.remotePort.toInt()
          )
        val ss = ServerSocket()
        ss.reuseAddress = true
        ss.bind(InetSocketAddress(params.localHost, params.localPort))
        ss.use {
          ssh.newLocalPortForwarder(params, ss).listen()
        }
      }
    } catch (e: Exception) {
      logd("error $e")
      sshSessionState.error.value = e.toString()
    }
  }

  fun hangup() {
    scope.launch(IO) {
      try {
        // i don't know what i am doing
        cmd?.signal(Signal.HUP)
        cmdJob?.cancel()
        cmd?.join()
        session?.close()
        ssh.disconnect()
      } catch (e: Exception) {
        logd("error: $e")
      }
    }
  }
}