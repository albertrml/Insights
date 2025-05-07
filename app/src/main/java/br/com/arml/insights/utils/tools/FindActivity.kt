package br.com.arml.insights.utils.tools

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}