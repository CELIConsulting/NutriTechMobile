package com.istea.nutritechmobile.helpers

import android.content.Context
import java.util.regex.Pattern
import android.util.Patterns
import android.widget.EditText

fun mailFormatIsValid(mail: String): Boolean {
    val pattern: Pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(mail).matches()
}

fun getTextFrom(editText: EditText) = editText.text.toString()

fun getTextFromResource(context: Context, stringResourceId: Int): String {
    return context.resources.getString(stringResourceId);
}