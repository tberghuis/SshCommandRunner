package dev.tberghuis.sshcommandrunner.tmp

import dev.tberghuis.sshcommandrunner.BuildConfig
import dev.tberghuis.sshcommandrunner.util.logd
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import net.schmizz.sshj.SSHClient
import net.schmizz.sshj.common.IOUtils
import net.schmizz.sshj.connection.channel.direct.Parameters
import net.schmizz.sshj.transport.verification.PromiscuousVerifier


// step 1 hardcode
class SshController(private val scope: CoroutineScope) {
  // stick state here 1:1 mapping mvp

  val host = "192.168.0.120"
  val user = "tom"
  val password = BuildConfig.tmppipassword


  fun run() {
    logd("SshController run")

    scope.launch(IO) {
//      exampleExec()
      exampleLocalPF()
    }

  }

  // from Exec.java
  private fun exampleExec() {
    val ssh = SSHClient()
    ssh.addHostKeyVerifier(PromiscuousVerifier())
    ssh.connect(host)
    ssh.authPassword(user, password)
    val session = ssh.startSession()
    val cmd = session.exec("ping -c 1 google.com")
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
    ssh.connect(host)
    ssh.authPassword(user, password)

    val params = Parameters("127.0.0.1", 8081, "127.0.0.1", 8081)
    val ss = ServerSocket()
    ss.reuseAddress = true
    ss.bind(InetSocketAddress(params.localHost, params.localPort))
    ss.use { ss ->
      ssh.newLocalPortForwarder(params, ss).listen()
    }
  }

}