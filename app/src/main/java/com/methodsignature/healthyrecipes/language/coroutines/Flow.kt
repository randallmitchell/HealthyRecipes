package com.methodsignature.healthyrecipes.language.coroutines

import com.methodsignature.healthyrecipes.log.logError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch

suspend fun <T> Flow<T>.catchWithLogging(
    logMessage: String,
    action: suspend FlowCollector<T>.(Throwable) -> Unit
): Flow<T> = catch { throwable ->
    logError(logMessage, throwable)
    action(throwable)
}