package com.evelin.juego.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.evelin.juego.data.model.Challenge
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "evelin_challenges_prefs")

class UserPreferences(private val context: Context) {

    companion object {
        private val CHALLENGES_KEY = stringSetPreferencesKey("saved_challenges")
    }

    val challengesFlow: Flow<List<Challenge>> = context.dataStore.data.map { preferences ->
        val stringSet = preferences[CHALLENGES_KEY] ?: emptySet()
        stringSet.map { entry ->
            val parts = entry.split("|||", limit = 2)
            if (parts.size == 2) {
                Challenge(id = parts[0], text = parts[1])
            } else {
                Challenge(text = entry)
            }
        }
    }

    suspend fun saveChallenges(challenges: List<Challenge>) {
        context.dataStore.edit { preferences ->
            val stringSet = challenges.map { "${it.id}|||${it.text}" }.toSet()
            preferences[CHALLENGES_KEY] = stringSet
        }
    }
}
