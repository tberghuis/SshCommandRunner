package dev.tberghuis.sshcommandrunner.tmp

import dev.tberghuis.sshcommandrunner.data.Command
import dev.tberghuis.sshcommandrunner.tmp2.SshSessionState
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
      cmd = session!!.exec(command.command)
      val sshFlow = cmd!!.inputStream.bufferedReader().lineSequence().asFlow()
      cmdJob = scope.launch(IO) {
        sshFlow.cancellable().collect {
          logd("line: $it")
          sshSessionState.commandOutput.value += it
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


//  fun run() {
//    logd("SshController run")
//
//    scope.launch(IO) {
//      exampleExec()
//    }
//  }

  // from Exec.java
  private fun exampleExec() {
    val ssh = SSHClient()
    ssh.addHostKeyVerifier(PromiscuousVerifier())
    ssh.connect(command.host)
    ssh.authPassword(command.user, command.password)
    val session = ssh.startSession()
    val cmd = session.exec(command.command)
    val output = IOUtils.readFully(cmd.inputStream).toString()
    logd("exampleExec output $output")
    // timeout (5) is maximum time to wait
    cmd.join(5, TimeUnit.SECONDS)
    val exitStatus = cmd.exitStatus
    logd("exampleExec exitStatus $exitStatus")
  }

  private fun exampleLocalPF() {
    val ssh = SSHClient()
    ssh.addHostKeyVerifier(PromiscuousVerifier())
    ssh.connect(command.host)
    ssh.authPassword(command.user, command.password)

    val params = Parameters("127.0.0.1", 8081, "127.0.0.1", 8081)
    val ss = ServerSocket()
    ss.reuseAddress = true
    ss.bind(InetSocketAddress(params.localHost, params.localPort))
    ss.use {
      ssh.newLocalPortForwarder(params, ss).listen()
    }
  }
}