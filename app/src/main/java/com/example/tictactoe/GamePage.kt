package com.example.tictactoe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoe.aiPlayer.AI
import com.example.tictactoe.aiPlayer.HUMAN
import com.example.tictactoe.aiPlayer.bestTurn
import com.example.tictactoe.aiPlayer.board
import com.example.tictactoe.aiPlayer.checkWin
import com.example.tictactoe.aiPlayer.resetGame
import com.example.tictactoe.aiPlayer.winner


// GamePage.kt (updated)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamePage() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "TicTacToe",
                        fontSize = 26.sp,
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            letterSpacing = TextUnit(0.9f, TextUnitType.Sp),
                        )
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display winner
            winner?.let { result ->
                Text(
                    text = when (result) {
                        "tie" -> "Game Draw :>"
                        "O" -> "AI Won!!"
                        else -> "You Won!!"
                    },
                    fontSize = 24.sp,
                    modifier = Modifier.padding(16.dp),
                    color = Color.Blue
                )
            }

            // Game board
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(3) { row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        for (col in 0..2) {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                                    .clickable(
                                        enabled = winner == null && board[row][col].value == ' ',
                                        onClick = {
                                            board[row][col].value = HUMAN
                                            winner = checkWin()
                                            if (winner == null) bestTurn()
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = board[row][col].value.toString(),
                                    fontSize = 32.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = when (board[row][col].value) {
                                        HUMAN -> Color.Black
                                        AI -> Color.Blue
                                        else -> Color.Black
                                    }
                                )
                            }
                        }
                    }
                }
            }

            // Reset button
            Button(
                onClick = { resetGame() },
                modifier = Modifier.padding(16.dp),
                colors = ButtonDefaults.buttonColors(Color.Gray)
            ) {
                Text("New Game")
            }
        }
    }
}

