package com.deanuharatinu.rootdetection

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.deanuharatinu.rootdetector.RootDetector
import com.deanuharatinu.rootdetector.RootDetectorFactory
import com.deanuharatinu.rootdetector.RootDetectorImpl.Companion.USER_ID
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
  private lateinit var rootDetector: RootDetector
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    initRootDetector()
  }

  private fun initRootDetector() {
    this.rootDetector = RootDetectorFactory.createRootDetector(this, Firebase.crashlytics)

    lifecycleScope.launch {
      val isDeviceIndicatedAsRooted = async { rootDetector.isDeviceRooted() }

      if (isDeviceIndicatedAsRooted.await()) {
        // Set all the data that needed to sent to crashlytics
        val logData = mutableMapOf<String, String>()
        logData[USER_ID] = "userid_1234"
        logData["phone_number"] = "08123-1233-1231"

        showRootAlert { rootDetector.crashTheApp(logData) }
      }
    }
  }

  private fun showRootAlert(onDismiss: () -> Unit) {
    val alertDialog: AlertDialog = AlertDialog.Builder(this@MainActivity).create()
    alertDialog.setTitle("Alert")
    alertDialog.setMessage("Root terdeteksi pada device")
    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Dismiss") { dialog, _ -> dialog.dismiss() }
    alertDialog.setOnDismissListener { onDismiss.invoke() }
    alertDialog.show()
  }
}