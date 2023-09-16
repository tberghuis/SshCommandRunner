package dev.tberghuis.sshcommandrunner.tmp

import dev.tberghuis.sshcommandrunner.util.logd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RunningCommand(scope: CoroutineScope) {

  init {
    logd("init running command")
    scope.launch(IO) {
      delay(10000)
      logd("RunningCommand 10 secs")
      delay(10000)
      logd("RunningCommand 20 secs")
      delay(10000)
      logd("RunningCommand 30 secs")
    }
  }

}