package com.istea.nutritechmobile.helpers

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

object UIManager {
    fun showMessageShort(context: Context, message: String) {
        KeyboardUtil.hideKeyboard(context as Activity)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).apply {
            show()
        }
    }

    fun showMessageLong(context: Context, message: String) {
        KeyboardUtil.hideKeyboard(context as Activity)
        Toast.makeText(context, message, Toast.LENGTH_LONG).apply {
            show()
        }
    }
}

object KeyboardUtil {
    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

