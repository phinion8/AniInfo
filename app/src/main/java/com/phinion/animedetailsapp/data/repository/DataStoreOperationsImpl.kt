package com.phinion.animedetailsapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.phinion.animedetailsapp.domain.repository.DataStoreOperations
import com.phinion.animedetailsapp.utils.Constants.PREFERENCES_KEY
import com.phinion.animedetailsapp.utils.Constants.PREFERENCES_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException

//this extension variable will be used to access our data store
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStoreOperationsImpl(context: Context) : DataStoreOperations {

    //object that hold the key
    //we have to tell the data store preferences that under which key we want to store the boolean data
    private object PreferencesKey {
        val onBoardingKey = booleanPreferencesKey(name = PREFERENCES_KEY)
    }

    //this variable hold the datastore that we already have made above
    private val dataStore = context.dataStore

    override suspend fun saveOnBoardingState(completed: Boolean) {
        //this function will allow us to save the value in the data store to the specific key
        dataStore.edit { preference ->

            preference[PreferencesKey.onBoardingKey] = completed

        }
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        //this will catch some exception if we encounter one
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->

            //if on boarding state is null then it going to return false
            val onBoardingState = preferences[PreferencesKey.onBoardingKey] ?: false
            onBoardingState

        }
    }
}