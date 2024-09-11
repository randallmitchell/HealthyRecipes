package com.methodsignature.healthyrecipes.observability

import android.content.Context
import android.util.Log
import com.methodsignature.healthyrecipes.BuildConfig
import com.newrelic.agent.android.NewRelic
import timber.log.Timber

class NewRelicObservabilityLogTree() : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR && t != null) {
            NewRelic.recordHandledException(
                t,
                mapOf(
                    "tag" to tag,
                    "message" to message,
                )
            )
        }
    }

    // This builder just helps ensure proper initialization before log is planted.
    // It is also assumed here that this is the first call to the New Relic SDK.
    class Builder(private val applicationContext: Context) {
        fun build(): NewRelicObservabilityLogTree {
            NewRelic.withApplicationToken(BuildConfig.NEW_RELIC_API_KEY).apply {
                start(applicationContext)
            }
            return NewRelicObservabilityLogTree()
        }
    }
}