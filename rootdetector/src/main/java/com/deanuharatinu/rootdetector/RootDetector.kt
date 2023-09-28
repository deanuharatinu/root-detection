package com.deanuharatinu.rootdetector

interface RootDetector {
  /**
   * Boolean returned is used as indication that the device has been rooted
   * @return Boolean
   */
  suspend fun isDeviceRooted(): Boolean

  /**
   * Crash the app and sent log data to any crash analytic instance i.e: Crashlytics
   */
  fun crashTheApp(logData: Map<String, String>)
}