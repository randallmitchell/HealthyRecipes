package com.methodsignature.healthyrecipes.value

import com.methodsignature.healthyrecipes.language.value.NonBlankString
import org.amshove.kluent.`should be equal to`
import org.junit.Test

class NonBlankStringTest {

    @Test
    fun `cannot be created from null string`() {
        NonBlankString.from(null) `should be equal to` null
    }
    @Test
    fun `cannot be created from empty string`() {
        blankStrings().forEach { emptyString ->
            NonBlankString.from(emptyString) `should be equal to` null
        }
    }

    private fun blankStrings() = arrayOf(
            " ",
            "\t",
            "\n",
    )
}