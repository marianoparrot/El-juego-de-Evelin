package com.evelin.juego.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.evelin.juego.data.model.Challenge
import com.evelin.juego.data.preferences.UserPreferences
import com.evelin.juego.data.repository.ChallengeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ChallengeRepository(UserPreferences(application))

    private val _challenges = MutableStateFlow<List<Challenge>>(emptyList())
    val challenges: StateFlow<List<Challenge>> = _challenges.asStateFlow()

    private val _currentChallenge = MutableStateFlow<String>("Pulsa el botón para descubrir un reto")
    val currentChallenge: StateFlow<String> = _currentChallenge.asStateFlow()

    private val pendingIndices = mutableListOf<Int>()

    init {
        viewModelScope.launch {
            repository.challenges.collect { list ->
                _challenges.value = list
                resetPendingIndices(list.size)
            }
        }
    }

    private fun resetPendingIndices(size: Int) {
        pendingIndices.clear()
        if (size > 0) {
            pendingIndices.addAll(0 until size)
            pendingIndices.shuffle()
        }
    }

    fun pickRandomChallenge() {
        val list = _challenges.value
        if (list.isEmpty()) {
            _currentChallenge.value = "No hay retos disponibles. ¡Añade uno nuevo!"
            return
        }

        if (pendingIndices.isEmpty()) {
            resetPendingIndices(list.size)
        }

        val nextIndex = pendingIndices.removeAt(0)
        _currentChallenge.value = list[nextIndex].text
    }

    fun addChallenge(text: String) {
        val updated = _challenges.value.toMutableList()
        updated.add(Challenge(text = text.trim()))
        saveList(updated)
    }

    fun editChallenge(id: String, newText: String) {
        val updated = _challenges.value.map {
            if (it.id == id) it.copy(text = newText.trim()) else it
        }
        saveList(updated)
    }

    fun deleteChallenge(id: String) {
        val updated = _challenges.value.filter { it.id != id }
        saveList(updated)
    }

    fun deleteAllChallenges() {
        saveList(emptyList())
        _currentChallenge.value = "Pulsa el botón para descubrir un reto"
    }

    private fun saveList(list: List<Challenge>) {
        viewModelScope.launch {
            repository.saveAll(list)
            resetPendingIndices(list.size)
        }
    }
}
