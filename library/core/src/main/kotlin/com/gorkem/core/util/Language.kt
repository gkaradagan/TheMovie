package com.gorkem.core.util

import android.content.res.Resources
import android.os.Build
import java.util.Locale

fun languageTag(): String {
    val locale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Resources.getSystem().configuration.locales.get(0)
    } else {
        Resources.getSystem().configuration.locale
    }
    return locale.toLanguageTag()
}
