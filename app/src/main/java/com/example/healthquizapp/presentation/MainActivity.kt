/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.example.healthquizapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    val questions = listOf(
        "Are you feeling healthy?",
        "Do you exercise regularly?",
        "Do you sleep enough?"
    )
    val currentQuestionIndex = remember { mutableStateOf(0) }
    val score = remember { mutableStateOf(0) }
    
    if (currentQuestionIndex.value >= questions.size) {
        val healthScore = (score.value * 10) / questions.size
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
                Text(
                    text = "Your health score is: $healthScore out of 10",
                )
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = questions[currentQuestionIndex.value])
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
            ) {
                Button(
                    onClick = {
                        score.value ++
                        currentQuestionIndex.value++
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Yes")
                }
                Button(
                    onClick = {
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