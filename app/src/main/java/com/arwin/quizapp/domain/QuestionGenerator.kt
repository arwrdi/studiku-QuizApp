package com.arwin.quizapp.domain

import kotlin.random.Random

class QuestionGenerator(
    private val random: Random = Random.Default
) {
    fun generateQuiz(difficulty: Difficulty, count: Int = 10): List<Question> =
        List(count) { generateQuestion(difficulty) }

    fun generateQuestion(difficulty: Difficulty): Question {
        val (text, answer) = when (difficulty) {
            Difficulty.EASY -> easyQuestion()
            Difficulty.MEDIUM -> mediumQuestion()
            Difficulty.HARD -> hardQuestion()
        }

        return Question(
            text = text,
            options = generateOptions(answer),
            correctAnswer = answer
        )
    }

    private fun easyQuestion(): Pair<String, Int> {
        val a = random.nextInt(1, 21)
        val b = random.nextInt(1, 21)
        return if (random.nextBoolean()) {
            "$a + $b = ?" to (a + b)
        } else {
            val high = maxOf(a, b)
            val low = minOf(a, b)
            "$high - $low = ?" to (high - low)
        }
    }

    private fun mediumQuestion(): Pair<String, Int> {
        val a = random.nextInt(2, 13)
        val b = random.nextInt(2, 13)
        return if (random.nextBoolean()) {
            "$a × $b = ?" to (a * b)
        } else {
            val result = random.nextInt(2, 13)
            val divisor = random.nextInt(2, 13)
            val dividend = result * divisor
            "$dividend ÷ $divisor = ?" to result
        }
    }

    private fun hardQuestion(): Pair<String, Int> {
        val a = random.nextInt(5, 31)
        val b = random.nextInt(2, 11)
        val c = random.nextInt(2, 11)
        return when (random.nextInt(3)) {
            0 -> "$a + $b × $c = ?" to (a + b * c)
            1 -> "$a × $b - $c = ?" to (a * b - c)
            else -> {
                val product = b * c
                "$product ÷ $b + $a = ?" to (c + a)
            }
        }
    }

    private fun generateOptions(correct: Int): List<Int> {
        val answers = linkedSetOf(correct)
        var attempts = 0

        while (answers.size < 4 && attempts < 100) {
            val spread = maxOf(4, kotlin.math.abs(correct) / 3 + 2)
            val candidate = correct + random.nextInt(-spread, spread + 1)
            if (candidate >= 0) answers += candidate
            attempts++
        }

        var fallback = 1
        while (answers.size < 4) {
            answers += correct + fallback
            fallback++
        }

        return answers.shuffled(random)
    }
}
