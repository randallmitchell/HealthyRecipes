package com.methodsignature.healthyrecipes.usecase

import com.methodsignature.healthyrecipes.service.api.RecipeService
import io.mockk.mockk
import org.junit.Test

class GetRecipesUseCaseTest {

    private val recipeService = mockk<RecipeService>()
    private val tested = GetRecipesUseCase(recipeService)

    @Test
    fun `GIVEN test are configured correctly, THEN this test will run successfully`() {
        val isValidTest = true
        assert(isValidTest)
    }
}
