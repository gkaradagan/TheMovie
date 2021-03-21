/*
 * Copyright 2021 Görkem Karadoğan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    startingContext: Fragment? = null,
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
