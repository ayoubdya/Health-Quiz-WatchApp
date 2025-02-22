package com.example.healthquizapp.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.healthquizapp.presentation.theme.HealthQuizAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            HealthQuizAppTheme {
                QuizScreen()
            }
        }
    }
}

@Composable
fun QuizScreen() {
    val context = LocalContext.current

    // Liste des questions avec leur coefficient et si "Yes" est bon ou mauvais
    val allQuestions = listOf(
        "Do you feel healthy?" to Pair(1.0, true),
        "Do you exercise daily?" to Pair(1.5, true),
        "Do you sleep enough?" to Pair(1.2, true),
        "Drink enough water?" to Pair(1.3, true),
        "Eat fruits, vegetables?" to Pair(1.4, true),
        "Avoid processed food?" to Pair(1.1, true),
        "Take breaks often?" to Pair(1.0, true),
        "Maintain good posture?" to Pair(1.3, true),
        "Have medical check-ups?" to Pair(1.5, true),
        "Manage stress well?" to Pair(1.4, true),
        "Do you smoke?" to Pair(2.0, false),  // "No" est la bonne réponse
        "Drink alcohol often?" to Pair(1.8, false),  // "No" est la bonne réponse
        "Practice meditation?" to Pair(1.2, true),
        "Have a balanced diet?" to Pair(1.3, true),
        "Limit screen before bed?" to Pair(1.1, true),
        "Exercise 30 min daily?" to Pair(1.5, true),
        "Stay hydrated daily?" to Pair(1.3, true),
        "Get enough sunlight?" to Pair(1.2, true),
        "Prioritize mental health?" to Pair(1.4, true),
        "Avoid too much caffeine?" to Pair(1.0, true)
    )

    val selectedQuestions = remember { allQuestions.shuffled().take(10) }
    val currentQuestionIndex = remember { mutableStateOf(0) }
    val score = remember { mutableStateOf(0.0) }
    val totalCoefficient = selectedQuestions.sumOf { it.second.first }

    if (currentQuestionIndex.value >= selectedQuestions.size) {
        val normalizedScore = (score.value * 10) / totalCoefficient
        Toast.makeText(context, "Your health score is: %.2f/10".format(normalizedScore), Toast.LENGTH_LONG).show()

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .background(color = MaterialTheme.colors.primary, shape = RoundedCornerShape(8.dp))
                    .padding(24.dp)
            ) {
                Text(text = "Your health score is: %.2f out of 10".format(normalizedScore))
            }
        }
    } else {
        val (question, data) = selectedQuestions[currentQuestionIndex.value]
        val coefficient = data.first
        val yesIsGood = data.second  // Indique si "Yes" est la bonne réponse

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = question,
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.body1,
                maxLines = 2
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
            ) {
                Button(
                    onClick = {
                        if (yesIsGood) {
                            score.value += coefficient // Ajoute du score si "Yes" est la bonne réponse
                        }
                        currentQuestionIndex.value++
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Yes")
                }
                Button(
                    onClick = {
                        if (!yesIsGood) {
                            score.value += coefficient // Ajoute du score si "No" est la bonne réponse
                        }
                        currentQuestionIndex.value++
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "No")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    HealthQuizAppTheme {
        QuizScreen()
    }
}
