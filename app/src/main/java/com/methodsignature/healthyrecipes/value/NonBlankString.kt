package com.methodsignature.healthyrecipes.value

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
