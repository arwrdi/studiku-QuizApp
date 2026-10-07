package com.arwin.quizapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arwin.quizapp.domain.Difficulty

@Composable
fun DifficultyScreen(onSelected: (Difficulty) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Select Difficulty", style = MaterialTheme.typography.headlineMedium)
        Difficulty.entries.forEach { difficulty ->
            Button(
                onClick = { onSelected(difficulty) },
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
            ) {
                Text(difficulty.label)
            }
        }
    }
}
