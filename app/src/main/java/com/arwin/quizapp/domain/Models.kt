package com.arwin.quizapp.domain

enum class Difficulty(val label: String) {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard")
}

data class Question(
    val text: String,
    val options: List<Int>,
    val correctAnswer: Int
)

data class QuizState(
    val difficulty: Difficulty = Difficulty.EASY,
    val questions: List<Question> = emptyList(),
    val currentIndex: Int = 0,
    val score: Int = 0,
    val bestScore: Int = 0,
    val isFinished: Boolean = false
) {
    val currentQuestion: Question?
        get() = questions.getOrNull(currentIndex)

    val progressText: String
        get() = if (questions.isEmpty()) "0 / 10" else "${currentIndex + 1} / ${questions.size}"
}
