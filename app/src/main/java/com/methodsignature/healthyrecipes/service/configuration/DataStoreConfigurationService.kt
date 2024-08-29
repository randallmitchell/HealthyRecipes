package com.methodsignature.healthyrecipes.service.configuration

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.methodsignature.healthyrecipes.service.api.ConfigurationService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreConfigurationService @Inject constructor(
    val context: Context
) : ConfigurationService {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

    override suspend fun setRecipeServiceAsSeeded() {
        context.dataStore.edit { settings ->
            settings[KEY_IS_RECIPE_SERVICE_SEEDED] = true
        }
    }

    override suspend fun observeIsRecipeServiceSeeded(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[KEY_IS_RECIPE_SERVICE_SEEDED] ?: false
        }
    }

    companion object {
        private const val DATA_STORE_NAME = "settings"
        private val KEY_IS_RECIPE_SERVICE_SEEDED = booleanPreferencesKey("is_recipe_service_seeded")
    }
}