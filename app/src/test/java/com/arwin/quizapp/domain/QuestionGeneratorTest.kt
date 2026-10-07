package com.arwin.quizapp.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random

class QuestionGeneratorTest {
    private val generator = QuestionGenerator(Random(42))

    @Test
    fun `quiz contains exactly ten questions`() {
        val questions = generator.generateQuiz(Difficulty.EASY)
        assertEquals(10, questions.size)
    }

    @Test
    fun `every question contains four unique options`() {
        Difficulty.entries.forEach { difficulty ->
            generator.generateQuiz(difficulty).forEach { question ->
                assertEquals(4, question.options.size)
                assertEquals(4, question.options.toSet().size)
            }
        }
    }

    @Test
    fun `correct answer exists exactly once`() {
        Difficulty.entries.forEach { difficulty ->
            generator.generateQuiz(difficulty).forEach { question ->
                assertEquals(1, question.options.count { it == question.correctAnswer })
            }
        }
    }

    @Test
    fun `hard questions use mixed operations`() {
        val questions = generator.generateQuiz(Difficulty.HARD, count = 20)
        assertTrue(
            questions.all { question ->
                listOf("+", "-", "×", "÷").count { operator ->
                    question.text.contains(operator)
                } >= 2
            }
        )
    }
}
