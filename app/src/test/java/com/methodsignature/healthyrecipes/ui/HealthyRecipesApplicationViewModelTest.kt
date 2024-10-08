package com.methodsignature.healthyrecipes.ui

import app.cash.turbine.test
import com.methodsignature.healthyrecipes.BaseTest
import com.methodsignature.healthyrecipes.usecase.UpdateAllRecipesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.junit.Test

class HealthyRecipesApplicationViewModelTest : BaseTest() {

    private val updateAllRecipesUseCase = mockk<UpdateAllRecipesUseCase>()
    private val tested = HealthyRecipesApplicationViewModel(
        updateAllRecipesUseCase = updateAllRecipesUseCase,
    )

    @Test
    fun `GIVEN the application is initialized THEN the database is seeded`() = runTest {
        coEvery { updateAllRecipesUseCase.run() } returns Unit
        tested.onApplicationLaunch()
        coVerify(exactly = 1) { updateAllRecipesUseCase.run() }
    }

    @Test
    fun `GIVEN the content flow is completed THEN the application is completed`() = runTest {
        tested.onContentFlowComplete()
        tested.navigationEvent.test {
            val mostRecentItem = expectMostRecentItem()
            mostRecentItem shouldBe HealthyRecipesApplicationViewModel.NavigationEvent.ApplicationComplete
        }
    }
}