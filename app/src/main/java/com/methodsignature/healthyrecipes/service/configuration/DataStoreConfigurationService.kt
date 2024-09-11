package com.methodsignature.healthyrecipes.service.configuration

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.methodsignature.healthyrecipes.service._api.configuration.ConfigurationService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataStoreConfigurationService @Inject constructor(
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher,
) : ConfigurationService {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

    override suspend fun getRecipeSeedState(): Flow<ConfigurationService.RecipeSeedState> =
        withContext(ioDispatcher) {
            context.dataStore.data.map { preferences ->
                val raw = preferences[KEY_RECIPE_SEED_STATE] ?: 0
                ConfigurationService.RecipeSeedState.fromInt(raw)
            }
        }

    override suspend fun setRecipeSeedState(value: ConfigurationService.RecipeSeedState) =
        withContext(ioDispatcher) {
            context.dataStore.edit { preferences ->
                preferences[KEY_RECIPE_SEED_STATE] = value.toInt()
            }
            Unit
        }

    companion object {
        private const val DATA_STORE_NAME = "settings"
        private val KEY_RECIPE_SEED_STATE = intPreferencesKey("recipe_seed_state")
    }
}