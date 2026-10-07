package com.arwin.quizapp.data

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.arwin.quizapp.domain.Difficulty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "quiz_preferences")

class BestScoreRepository(private val context: Context) {
    fun bestScore(difficulty: Difficulty): Flow<Int> =
        context.dataStore.data.map { preferences ->
            preferences[key(difficulty)] ?: 0
        }

    suspend fun saveIfHigher(difficulty: Difficulty, score: Int) {
        context.dataStore.edit { preferences ->
            val scoreKey = key(difficulty)
            val current = preferences[scoreKey] ?: 0
            if (score > current) preferences[scoreKey] = score
        }
    }

    private fun key(difficulty: Difficulty) =
        intPreferencesKey("best_score_${difficulty.name.lowercase()}")
}
