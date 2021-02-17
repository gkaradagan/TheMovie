package com.gorkem.core.ext

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.fragment.app.Fragment
import com.gorkem.core.presentation.navigation.Destination

fun Intent.clearStack() {
    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
}

fun Activity.navigate(
    destination: Destination,
    startingContext: Fragment? = null
) {
    Intent().apply {
        setClassName(this@navigate, destination.getClassName())
        destination.setIntent(this)
        try {
            if (destination.requestCode != -1) {
                startingContext?.startActivityForResult(this, destination.requestCode)
                    ?: startActivityForResult(this, destination.requestCode)
            } else {
                if (destination.isSingleTop) {
                    this.clearStack()
                }
                startingContext?.startActivity(this) ?: startActivity(this)
                if (destination.isFinishingAfterStart or destination.isSingleTop) {
                    finish()
                }
            }
        } catch (ignore: ActivityNotFoundException) {
        }
    }
}

internal fun Fragment.navigate(destination: Destination) {
    requireActivity().navigate(destination, this)
}