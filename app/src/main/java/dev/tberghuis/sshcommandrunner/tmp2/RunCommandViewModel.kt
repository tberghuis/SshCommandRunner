package dev.tberghuis.sshcommandrunner.tmp2

import android.annotation.SuppressLint
import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dev.tberghuis.sshcommandrunner.data.Command
import dev.tberghuis.sshcommandrunner.tmp.SshService
import dev.tberghuis.sshcommandrunner.util.logd
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

