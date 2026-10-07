package com.arwin.quizapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arwin.quizapp.domain.QuizState

@Composable
fun ResultScreen(
    state: QuizState,
    onReplay: () -> Unit,
    onHome: () -> Unit
) {
    val displayedBest = maxOf(state.bestScore, state.score)

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Quiz Complete", style = MaterialTheme.typography.headlineMedium)
        Text(
            "${state.score} / ${state.questions.size}",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = 20.dp)
        )
        Text("Best score (${state.difficulty.label}): $displayedBest / 10")

        Button(
            onClick = onReplay,
            modifier = Modifier.fillMaxWidth().padding(top = 28.dp)
        ) {
            Text("Play Again")
        }
        OutlinedButton(
            onClick = onHome,
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
        ) {
            Text("Back to Home")
        }
    }
}
