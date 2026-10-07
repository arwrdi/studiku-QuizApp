package com.arwin.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arwin.quizapp.data.BestScoreRepository
import com.arwin.quizapp.ui.screens.DifficultyScreen
import com.arwin.quizapp.ui.screens.HomeScreen
import com.arwin.quizapp.ui.screens.QuizScreen
import com.arwin.quizapp.ui.screens.ResultScreen
import com.arwin.quizapp.ui.theme.QuizAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            QuizAppTheme {
                val navController = rememberNavController()
                val repository = BestScoreRepository(applicationContext)
                val quizViewModel: QuizViewModel = viewModel(
                    factory = QuizViewModel.Factory(repository)
                )
                val state by quizViewModel.state.collectAsState()

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(onStart = { navController.navigate("difficulty") })
                    }
                    composable("difficulty") {
                        DifficultyScreen { difficulty ->
                            quizViewModel.startQuiz(difficulty)
                            navController.navigate("quiz")
                        }
                    }
                    composable("quiz") {
                        if (state.isFinished) {
                            navController.navigate("result") {
                                popUpTo("quiz") { inclusive = true }
                            }
                        } else {
                            QuizScreen(state = state, onAnswer = quizViewModel::submitAnswer)
                        }
                    }
                    composable("result") {
                        ResultScreen(
                            state = state,
                            onReplay = {
                                quizViewModel.restartQuiz()
                                navController.navigate("quiz") {
                                    popUpTo("result") { inclusive = true }
                                }
                            },
                            onHome = {
                                navController.navigate("home") {
                                    popUpTo("home") { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
