package com.methodsignature.healthyrecipes.value

/**
 * A value that represents text that is not empty, blank, or null.
 */
@Suppress("DataClassPrivateConstructor")
data class NonEmptyString private constructor(val value: String) {
    companion object {
        fun fromString(from: String?): NonEmptyString? {
            return if (from.isNullOrEmpty()) {
                null
            } else {
                NonEmptyString(from)
            }
        }
    }
}
