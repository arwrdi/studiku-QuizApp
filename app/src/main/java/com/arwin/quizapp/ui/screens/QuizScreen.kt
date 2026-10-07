package com.arwin.quizapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arwin.quizapp.domain.QuizState

@Composable
fun QuizScreen(state: QuizState, onAnswer: (Int) -> Unit) {
    val question = state.currentQuestion ?: return
    val progress = (state.currentIndex + 1).toFloat() / state.questions.size.coerceAtLeast(1)

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(state.difficulty.label)
            Text("Score: ${state.score}")
        }

        Text(
            "Question ${state.progressText}",
            modifier = Modifier.padding(top = 12.dp)
        )
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 28.dp)
        )

        Text(question.text, style = MaterialTheme.typography.headlineMedium)

        question.options.forEach { option ->
            Button(
                onClick = { onAnswer(option) },
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
            ) {
                Text(option.toString())
            }
        }
    }
}
