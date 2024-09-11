package com.methodsignature.healthyrecipes.language.coroutines

import com.methodsignature.healthyrecipes.observability.logError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import java.util.concurrent.CancellationException

suspend fun <T> Flow<T>.catchWithLogging(
    logMessage: String,
    action: suspend FlowCollector<T>.(Throwable) -> Unit
): Flow<T> = catch { throwable ->
    logError(logMessage, throwable)
    if (throwable is CancellationException) {
        throw throwable
    } else {
        action(throwable)
    }
}