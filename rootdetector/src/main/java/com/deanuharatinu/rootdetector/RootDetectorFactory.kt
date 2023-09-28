package com.deanuharatinu.rootdetector

import android.content.Context
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.scottyab.rootbeer.RootBeer

class RootDetectorFactory {
  companion object {
    fun createRootDetector(
      context: Context,
      firebaseCrashlytics: FirebaseCrashlytics
    ): RootDetector {
      val rootBeer = RootBeer(context)
      return RootDetectorImpl(rootBeer, firebaseCrashlytics)
    }
  }
}