package com.istea.nutritechmobile.helpers.extensions

import java.text.SimpleDateFormat
import java.util.*

private val formatter = SimpleDateFormat("dd/MM/yyyy")

fun Date.stringFromDate(): String {
    return formatter.format(this)
}

fun String.dateFromString(): Date? {
    return formatter.parse(this)
}


