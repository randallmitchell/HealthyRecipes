package com.methodsignature.healthyrecipes.value

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

/**
 * A value that represents text that is not empty, blank, or null.
 */
@Suppress("DataClassPrivateConstructor")
data class NonBlankString private constructor(val value: String) {
    companion object {
        fun from(from: String?): NonBlankString? {
            return if (from.isNullOrBlank()) {
                null
            } else {
                NonBlankString(from)
            }
        }
    }
}

class NonBlankStringMoshiAdapter {
    @ToJson
    fun toJson(nonBlankString: NonBlankString): String = nonBlankString.value

    @FromJson
    fun fromJson(string: String): NonBlankString = NonBlankString.from(string)!!
}
