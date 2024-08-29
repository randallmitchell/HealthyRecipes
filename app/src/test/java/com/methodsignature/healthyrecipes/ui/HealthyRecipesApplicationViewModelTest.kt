package com.methodsignature.healthyrecipes.ui

import com.methodsignature.healthyrecipes.BaseTest
import com.methodsignature.healthyrecipes.usecase.SeedRecipeListUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class HealthyRecipesApplicationViewModelTest : BaseTest() {

    private val seedRecipeListUseCase = mockk<SeedRecipeListUseCase>()
    private val tested = HealthyRecipesApplicationViewModel(
        seedRecipeListUseCase = seedRecipeListUseCase,
    )

    @Test
    fun `GIVEN the application is initialized THEN the database is seeded`() = runTest {
        coEvery { seedRecipeListUseCase.run() } returns Unit
        tested.onApplicationLaunch()
        coVerify(exactly = 1) { seedRecipeListUseCase.run() }
    }
}