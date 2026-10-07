package com.arwin.quizapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.arwin.quizapp.data.BestScoreRepository
import com.arwin.quizapp.domain.Difficulty
import com.arwin.quizapp.domain.QuestionGenerator
import com.arwin.quizapp.domain.QuizState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class QuizViewModel(
    private val repository: BestScoreRepository,
    private val generator: QuestionGenerator = QuestionGenerator()
) : ViewModel() {

    private val _state = MutableStateFlow(QuizState())
    val state: StateFlow<QuizState> = _state.asStateFlow()

    private var bestScoreJob: Job? = null

    fun startQuiz(difficulty: Difficulty) {
        _state.value = QuizState(
            difficulty = difficulty,
            questions = generator.generateQuiz(difficulty)
        )
        observeBestScore(difficulty)
    }

    fun submitAnswer(answer: Int) {
        val current = _state.value
        val question = current.currentQuestion ?: return
        if (current.isFinished) return

        val newScore = current.score + if (answer == question.correctAnswer) 1 else 0
        val isLastQuestion = current.currentIndex == current.questions.lastIndex

        if (isLastQuestion) {
            _state.value = current.copy(score = newScore, isFinished = true)
            viewModelScope.launch {
                repository.saveIfHigher(current.difficulty, newScore)
            }
        } else {
            _state.value = current.copy(
                currentIndex = current.currentIndex + 1,
                score = newScore
            )
        }
    }

    fun restartQuiz() {
        startQuiz(_state.value.difficulty)
    }

    private fun observeBestScore(difficulty: Difficulty) {
        bestScoreJob?.cancel()
        bestScoreJob = viewModelScope.launch {
            repository.bestScore(difficulty).collectLatest { best ->
                _state.value = _state.value.copy(bestScore = best)
            }
        }
    }

    class Factory(
        private val repository: BestScoreRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return QuizViewModel(repository) as T
        }
    }
}
