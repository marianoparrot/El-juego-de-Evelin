package com.evelin.juego.data.repository

import com.evelin.juego.data.model.Challenge
import com.evelin.juego.data.preferences.UserPreferences
import kotlinx.coroutines.flow.Flow

class ChallengeRepository(private val preferences: UserPreferences) {

    val challenges: Flow<List<Challenge>> = preferences.challengesFlow

    suspend fun saveAll(challenges: List<Challenge>) {
        preferences.saveChallenges(challenges)
    }
}
