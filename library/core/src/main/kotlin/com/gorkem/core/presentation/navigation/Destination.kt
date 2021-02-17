package com.gorkem.core.presentation.navigation

import android.content.Intent

abstract class Destination {
  abstract fun getClassName(): String
  abstract fun setIntent(intent: Intent)
  var requestCode = -1
  var isSingleTop = false
  var isFinishingAfterStart = false
}
