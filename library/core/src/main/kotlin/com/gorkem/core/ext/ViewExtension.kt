/*
 * Copyright 2020 Görkem Karadoğan
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
@file:Suppress("detekt.TooManyFunctions")

package com.gorkem.core.ext

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Base64
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.io.ByteArrayOutputStream
import java.io.IOException

/**
 * Extension method to provide simpler access to {@link View#getResources()#getString(int)}.
 */
fun View.getString(stringResId: Int): String = resources.getString(stringResId)

/**
 * Extension method to show a keyboard for View.
 */
fun View.showKeyboard() {
  val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  this.requestFocus()
  imm.showSoftInput(this, 0)
}

/**
 * Try to hide the keyboard and returns whether it worked
 * https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
 */
fun View.hideKeyboard(): Boolean {
  try {
    val inputMethodManager =
      context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
  } catch (ignored: RuntimeException) {
  }
  return false
}

/**
 * Extension method to remove the required boilerplate for running code after a view has been
 * inflated and measured.
 *
 * @author Antonio Leiva
 * @see <a href="https://antonioleiva.com/kotlin-ongloballayoutlistener/>Kotlin recipes: OnGlobalLayoutListener</a>
 */
inline fun <T : View> T.afterMeasured(crossinline f: T.() -> Unit) {
  viewTreeObserver.addOnGlobalLayoutListener(
    object : OnGlobalLayoutListener {
      override fun onGlobalLayout() {
        if (measuredWidth > 0 && measuredHeight > 0) {
          viewTreeObserver.removeOnGlobalLayoutListener(this)
          f()
        }
      }
    }
  )
}

/**
 * Extension method to update padding of view.
 *
 */
fun View.updatePadding(
  paddingStart: Int = getPaddingStart(),
  paddingTop: Int = getPaddingTop(),
  paddingEnd: Int = getPaddingEnd(),
  paddingBottom: Int = getPaddingBottom()
) {
  setPaddingRelative(paddingStart, paddingTop, paddingEnd, paddingBottom)
}

/**
 * Extension method to set View's left padding.
 */
fun View.setPaddingLeft(value: Int) = setPadding(value, paddingTop, paddingRight, paddingBottom)

/**
 * Extension method to set View's right padding.
 */
fun View.setPaddingRight(value: Int) = setPadding(paddingLeft, paddingTop, value, paddingBottom)

/**
 * Extension method to set View's top padding.
 */
fun View.setPaddingTop(value: Int) =
  setPaddingRelative(paddingStart, value, paddingEnd, paddingBottom)

/**
 * Extension method to set View's bottom padding.
 */
fun View.setPaddingBottom(value: Int) =
  setPaddingRelative(paddingStart, paddingTop, paddingEnd, value)

/**
 * Extension method to set View's start padding.
 */
fun View.setPaddingStart(value: Int) =
  setPaddingRelative(value, paddingTop, paddingEnd, paddingBottom)

/**
 * Extension method to set View's end padding.
 */
fun View.setPaddingEnd(value: Int) =
  setPaddingRelative(paddingStart, paddingTop, value, paddingBottom)

/**
 * Extension method to set View's horizontal padding.
 */
fun View.setPaddingHorizontal(value: Int) =
  setPaddingRelative(value, paddingTop, value, paddingBottom)

/**
 * Extension method to set View's vertical padding.
 */
fun View.setPaddingVertical(value: Int) = setPaddingRelative(paddingStart, value, paddingEnd, value)

/**
 * Extension method to set View's height.
 */
fun View.setHeight(value: Int) {
  val lp = layoutParams
  lp?.let {
    lp.height = value
    layoutParams = lp
  }
}

/**
 * Extension method to set View's width.
 */
fun View.setWidth(value: Int) {
  val lp = layoutParams
  lp?.let {
    lp.width = value
    layoutParams = lp
  }
}

/**
 * Extension method to resize View with height & width.
 */
fun View.resize(width: Int, height: Int) {
  val lp = layoutParams
  lp?.let {
    lp.width = width
    lp.height = height
    layoutParams = lp
  }
}

/**
 * Show the view  (visibility = View.VISIBLE)
 */
fun View.show(): View {
  if (visibility != View.VISIBLE) {
    visibility = View.VISIBLE
  }
  return this
}

/**
 * Show the view if [condition] returns true
 * (visibility = View.VISIBLE)
 */
inline fun View.showIf(condition: () -> Boolean): View {
  if (visibility != View.VISIBLE && condition()) {
    visibility = View.VISIBLE
  }
  return this
}

/**
 * Hide the view. (visibility = View.INVISIBLE)
 */
fun View.hide(): View {
  if (visibility != View.INVISIBLE) {
    visibility = View.INVISIBLE
  }
  return this
}

/**
 * Hide the view if [condition] returns true
 * (visibility = View.INVISIBLE)
 */
inline fun View.hideIf(condition: () -> Boolean): View {
  if (visibility != View.INVISIBLE && condition()) {
    visibility = View.INVISIBLE
  }
  return this
}

/**
 * Remove the view (visibility = View.GONE)
 */
fun View.remove(): View {
  if (visibility != View.GONE) {
    visibility = View.GONE
  }
  return this
}

/**
 * Remove the view if [condition] returns true
 * (visibility = View.GONE)
 */
inline fun View.removeIf(condition: () -> Boolean): View {
  if (visibility != View.GONE && condition()) {
    visibility = View.GONE
  }
  return this
}

/**
 * Toggle a view's visibility
 */
fun View.toggleVisibility(): View {
  visibility = if (visibility == View.VISIBLE) {
    View.INVISIBLE
  } else {
    View.INVISIBLE
  }
  return this
}

/**
 * Extension method to get a view as bitmap.
 */
fun View.getBitmap(): Bitmap {
  val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
  val canvas = Canvas(bmp)
  draw(canvas)
  canvas.save()
  return bmp
}

fun View.fadeIn() {
  val animationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
  apply {
    show()
    alpha = 0f
    animate()
      .alpha(1f)
      .setDuration(animationDuration.toLong())
      .setListener(null)
  }
}

fun View.fadeOut() {
  val animationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
  apply {
    animate()
      .alpha(0f)
      .setDuration(animationDuration.toLong())
      .setListener(
        object : AnimatorListenerAdapter() {
          override fun onAnimationEnd(animation: Animator) {
            remove()
          }
        }
      )
  }
}

/**
 * Extension method to get value from EditText.
 */
val EditText.value
  get() = text.toString()

/**
 * Extension method to get base64 string for Bitmap.
 */
fun Bitmap.toBase64(): String {
  var result = ""
  val baos = ByteArrayOutputStream()
  try {
    compress(Bitmap.CompressFormat.JPEG, 100, baos)
    baos.flush()
    baos.close()
    val bitmapBytes = baos.toByteArray()
    result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT)
  } catch (e: IOException) {
    e.printStackTrace()
  } finally {
    try {
      baos.flush()
      baos.close()
    } catch (e: IOException) {
      e.printStackTrace()
    }
  }
  return result
}

class DebounceOnClickListener(
  private val interval: Long,
  private val listenerBlock: (View) -> Unit
) : View.OnClickListener {
  private var lastClickTime = 0L

  override fun onClick(v: View) {
    val time = System.currentTimeMillis()
    if (time - lastClickTime >= interval) {
      lastClickTime = time
      listenerBlock(v)
    }
  }
}

fun View.setOnClickListener(debounceInterval: Long, listenerBlock: (View) -> Unit) =
  setOnClickListener(DebounceOnClickListener(debounceInterval, listenerBlock))
