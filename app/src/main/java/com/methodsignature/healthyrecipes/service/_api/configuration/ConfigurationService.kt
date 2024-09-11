package com.methodsignature.healthyrecipes.service._api.configuration

import kotlinx.coroutines.flow.Flow

/**
 * Persists app configuration information such as the latest state of key values.
 */
interface ConfigurationService {

    enum class RecipeSeedState {
        /**
         * No seed data has been set.
         */
        NOT_SEEDED,

        /**
         * Seed data set from hard coded data.
         */
        HARD_CODED,

        /**
         * Hard coded data is no longer present and remote content set.
         */
        SEED_PROCESSES_COMPLETE;

        fun toInt(): Int {
            return when (this) {
                NOT_SEEDED -> 0
                HARD_CODED -> 1
                SEED_PROCESSES_COMPLETE -> 2
            }
        }

        companion object {
            fun fromInt(value: Int): RecipeSeedState {
                return when (value) {
                    0 -> NOT_SEEDED
                    1 -> HARD_CODED
                    2 -> SEED_PROCESSES_COMPLETE
                    else -> throw IllegalStateException("Cannot convert value $value to SeedState.")
                }
            }
        }
    }

    suspend fun getRecipeSeedState(): Flow<RecipeSeedState>

    /**
     * Sets the seed state of the recipe data.
     */
    suspend fun setRecipeSeedState(value: RecipeSeedState)
}