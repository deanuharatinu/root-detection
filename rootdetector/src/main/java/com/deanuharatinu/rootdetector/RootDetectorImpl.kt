package com.deanuharatinu.rootdetector

import androidx.annotation.WorkerThread
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.scottyab.rootbeer.RootBeer

class RootDetectorImpl(
  private val rootBeer: RootBeer,
  private val crashlytics: FirebaseCrashlytics,
) : RootDetector {
  @WorkerThread
  override suspend fun isDeviceRooted(): Boolean {
    return rootBeer.isRooted
  }

  override fun crashTheApp(logData: Map<String, String>) {
    logData[USER_ID]?.let { userId ->
      crashlytics.setUserId(userId)
    }

    logData.forEach { data ->
      if (data.key != USER_ID) {
        crashlytics.setCustomKey(data.key, data.value)
      }
    }

    throw RootedDeviceException()
  }

  companion object {
    const val USER_ID = "user_id"
  }
}