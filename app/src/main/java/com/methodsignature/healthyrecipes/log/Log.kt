package com.methodsignature.healthyrecipes.log

import android.util.Log
import timber.log.Timber

fun logAssertion(message: String, throwable: Throwable? = null, vararg args: Any?) {
    log(Log.ASSERT, message, throwable, args)
}

fun logError(message: String, throwable: Throwable? = null, vararg args: Any?) {
    log(Log.ERROR, message, throwable, args)
}

fun logVerbosely(message: String, throwable: Throwable? = null, vararg args: Any?) {
    log(Log.VERBOSE, message, throwable, args)
}

fun logWarning(message: String, throwable: Throwable? = null, vararg args: Any?) {
    log(Log.WARN, message, throwable, args)
}

fun logDebug(message: String, throwable: Throwable? = null, vararg args: Any?) {
    log(Log.DEBUG, message, throwable, args)
}

fun log(priority: Int, message: String, throwable: Throwable? = null, vararg args: Any?) {
    Timber.Forest.log(priority, throwable, message, args)
}