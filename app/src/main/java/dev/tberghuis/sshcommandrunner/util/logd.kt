package dev.tberghuis.sshcommandrunner.util

import android.util.Log
import dev.tberghuis.sshcommandrunner.BuildConfig

// todo use KSP to remove from release builds ???
fun logd(s: String) {
  if (BuildConfig.DEBUG) Log.d("xxx", s)
}